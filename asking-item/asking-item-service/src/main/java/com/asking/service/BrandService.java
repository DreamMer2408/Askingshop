package com.asking.service;

import com.asking.item.pojo.Brand;
import com.asking.mapper.BrandMapper;
import com.asking.common.pojo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import sun.util.resources.ga.LocaleNames_ga;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName BrandService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/4 20:40
 * @Version 1.0
 **/
@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询品牌信息
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    public PageResult<Brand> queryBrandByPage(Integer page,Integer rows,String sortBy,Boolean desc,String key) {
        //分页
        PageHelper.startPage(page, rows);
        //查询
        Example example = new Example(Brand.class);
        if (!StringUtils.isNullOrEmpty(key)) {
            example.createCriteria().andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        //排序
        if (!StringUtils.isNullOrEmpty(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //创建pageInfo
        List<Brand> list=brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        //返回分页结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 添加品牌
     * @param brand
     * @param cids
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand, List<Long> cids){
        //新增品牌
        this.brandMapper.insertSelective(brand);
        //新增品牌信息到中间表
        for (Long cid:cids){
            this.brandMapper.insertCategoryBrand(cid,brand.getId());
        }
    }

    /**
     * 修改brand，并维护中间表
     * @param brand
     * @param cids
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand, List<Long> cids){
        //删除中间表原来的数据
        deleteByBrandIdInCategoryBrand(brand.getId());

        //修改p品牌信息
        brandMapper.updateByPrimaryKeySelective(brand);

        //维护品牌和分类中间表
        for (long cid:cids){
            brandMapper.insertCategoryBrand(cid,brand.getId());
        }
    }

    private void deleteByBrandIdInCategoryBrand(long bid){
        brandMapper.deleteByBrandIdInCategoryBrand(bid);
    }

    /**
     * 根据商品分类id查询品牌
     * @param cid
     * @return
     */
    public List<Brand> queryBrandByCid(Long cid){
        return brandMapper.qureyBrandByCid(cid);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(long id){

        brandMapper.deleteByPrimaryKey(id);

        brandMapper.deleteByBrandIdInCategoryBrand(id);
    }
}
