package com.asking.controller;

import com.asking.item.bo.SpuBo;
import com.asking.service.GoodsService;
import com.asking.common.pojo.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName GoodsController
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/7 19:12
 * @Version 1.0
 **/
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    private static final Logger logger= LoggerFactory.getLogger(GoodsController.class);

    /**
     * 分页查询商品信息
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @param saleable
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "rows",defaultValue = "5")int rows,
            @RequestParam(value = "sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",defaultValue = "false")boolean desc,
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "saleable",defaultValue = "true")boolean saleable){
        PageResult<SpuBo> result=goodsService.querySpuByPage(page,rows,sortBy,desc,key,saleable);
        if (result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 新增商品信息
     * @param spuBo
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据Id 查询商品
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    public ResponseEntity<SpuBo> queryGoodsById(@PathVariable("id")long id){
        SpuBo spuBo=goodsService.queryGoodsById(id);
        if (spuBo==null){
            logger.info(HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info(HttpStatus.OK.toString());
        return ResponseEntity.ok(spuBo);
    }

    /**
     * 修改商品
     * @param spuBo
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 删除商品
     * @param idString
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteGoods(@PathVariable("id")String idString){
        String separator="-";
        if (idString.contains(separator)){
            String[] goodsId=idString.split(separator);
            for (String id:goodsId){
                goodsService.deleteGoods(Long.parseLong(id));
            }
        }else {
            goodsService.deleteGoods(Long.parseLong(idString));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 商品上下架
     * @param id
     * @return
     */
    @PutMapping("/spu/out/{id}")
    public ResponseEntity<Void> goodsSoldOut(@PathVariable("id")Long id){
        goodsService.goodsSoldOut(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
