package com.asking.controller;

import com.asking.item.pojo.SpuBo;
import com.asking.service.GoodsService;
import com.askingshop.common.pojo.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mysql.cj.conf.PropertyKey.logger;

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
}
