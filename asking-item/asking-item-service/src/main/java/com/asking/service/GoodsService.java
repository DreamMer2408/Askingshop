package com.asking.service;

import com.asking.item.pojo.*;
import com.asking.mapper.*;
import com.askingshop.common.pojo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GoodsService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/7 19:16
 * @Version 1.0
 **/
@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final Logger logger= LoggerFactory.getLogger(GoodsService.class);
    public PageResult<SpuBo> querySpuByPage(int page,int rows,String sortBy,boolean desc,String key,boolean saleable){
       logger.info("开始商品分页查询");
       logger.info("传入参数-->page:{},rows:{},sortBy:{},desc:{},key:{},saleable:{}",page,rows,sortBy,desc,key,saleable);
        //1.查询Spu，分页查询，最多100条
        PageHelper.startPage(page,rows);
        //2.创建查询条件
        Example example=new Example(Spu.class);
        Example.Criteria criteria=example.createCriteria();
        //3.条件过滤
        //3.1 是否过滤上下架
        if(StringUtils.isNotBlank(sortBy)){
            criteria.orEqualTo("saleable",saleable);
        }
        //3.2 是否模糊查询
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        //3.3 是否排序
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+(desc?" desc":" asc"));
        }
        Page<Spu> pageInfo= (Page<Spu>) spuMapper.selectByExample(example);
        //将Spu变成SpuBo
        List<SpuBo> list=pageInfo.getResult().stream().map(spu -> {
            SpuBo spuBo=new SpuBo();
            //属性拷贝
            BeanUtils.copyProperties(spu,spuBo);
            //查询Spu的商品分类名称，各级分类
            List<String> nameList=categoryService.queryNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
            //拼接名字，并存入
            spuBo.setCname(StringUtils.join(nameList,"/"));
            //查询品牌名称
            Brand brand=brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            return spuBo;
        }).collect(Collectors.toList());
        logger.info("商品分页结束");
        return new PageResult<>(pageInfo.getTotal(),list);
    }

    /**
     * 保存商品
     * @param spuBo
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(SpuBo spuBo){
        //保存spu
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getLastUpdateTime());
        spuMapper.insert(spuBo);

        //保存spu详情
        SpuDetail spuDetail=spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        logger.info(spuDetail.getSpecifications().length()+"");
        spuDetailMapper.insert(spuDetail);

        //保存Sku和库存信息
        saveSkuAndStock(spuBo.getSkus(),spuBo.getId());

        //发送消息到mq
        sendMessage(spuBo.getId(),"insert");
    }

    private void saveSkuAndStock(List<Sku> skus,long id){
        for(Sku sku:skus){
            if (!sku.getEnable()){
                continue;
            }
            //保存Sku
            sku.setSpuId(id);
            //默认不参与任何促销
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insert(sku);

            //保存库存信息
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        }
    }

    /**
     * 更新商品信息
     * @param spuBo
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateGoods(SpuBo spuBo){

        //更新spu
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setLastUpdateTime(new Date());
        spuMapper.updateByPrimaryKeySelective(spuBo);

        //更新spu详情
        SpuDetail spuDetail=spuBo.getSpuDetail();
        String oldTemp=spuDetailMapper.selectByPrimaryKey(spuBo.getId()).getSpecTemplate();
        if (spuDetail.getSpecTemplate().equals(oldTemp)){
            //更新sku和库存信息
            //updateSkuAndStock(spuBo.getSkus(),spuBo.getId(),"true");
        }else {
            //updateSkuAndStock(spuBo.getSkus(),spuBo.getId(),"false");
        }
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        //发送消息到mq
        sendMessage(spuBo.getId(),"update");
    }

    /**
     * 商品删除二合一（多个、单个）
     * @param id
     */
    public void deleteGoods(long id){
        //删除spu表中的数据
        spuMapper.deleteByPrimaryKey(id);

        //删除spu_detail中的数据
        Example example=new Example(SpuDetail.class);
        example.createCriteria().andEqualTo("spuId",id);
        spuDetailMapper.deleteByExample(example);

        List<Sku> skuList=skuMapper.selectByExample(example);
        for (Sku sku:skuList) {
            //删除sku中的数据
            skuMapper.deleteByPrimaryKey(sku.getId());
            //删除stock中的数据
            stockMapper.deleteByPrimaryKey(sku.getId());
        }

        //发送消息到mq
        sendMessage(id,"delete");
    }
    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    public SpuBo queryGoodsById(long id){
        logger.info("查询商品信息，id:{}",id);
        Spu spu= spuMapper.selectByPrimaryKey(id);
        if (spu==null){
            logger.info("spu查询为空!");
        }
        logger.info("商品Spu信息:{}",spu.toString());
        SpuDetail spuDetail=spuDetailMapper.selectByPrimaryKey(spu.getId());
        if (spuDetail==null){
            logger.info("spuDetail查询为空!");
        }

        Example example=new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId",spu.getId());
        List<Sku> skus=skuMapper.selectByExample(example);
        List<Long> skuIdList=new ArrayList<>();
        for (Sku sku:skus){
            skuIdList.add(sku.getId());
        }

        List<Stock> stocks=stockMapper.selectByIdList(skuIdList);
        for (Sku sku:skus){
            for (Stock stock:stocks){
                if (sku.getId().equals(stock.getSkuId())){
                    sku.setStock(stock.getSkuId());
                }
            }
        }

        SpuBo spuBo=new SpuBo();
        spuBo.setSpuDetail(spuDetail);
        spuBo.setSkus(skus);
        logger.info("查询商品信息结束");
        return spuBo;
    }

    /**
     * 发送消息到mq，生产者
     * @param id
     * @param type
     */
    public void sendMessage(Long id,String type){
        logger.info("开始发送{}商品信息，商品id:{}",type,id);
        try {
            amqpTemplate.convertAndSend("item."+type,id);
        }catch (Exception e){
            logger.error("{}商品信息发送异常，商品id：{}",type,id);
        }
    }
}
