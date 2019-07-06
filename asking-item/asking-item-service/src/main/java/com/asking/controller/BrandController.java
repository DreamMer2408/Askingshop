package com.asking.controller;

import com.asking.item.pojo.Brand;
import com.asking.item.pojo.Category;
import com.asking.service.BrandService;
import com.askingshop.common.pojo.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName BrandController
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/4 20:46
 * @Version 1.0
 **/
@Controller
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    private static final Logger logger=LoggerFactory.getLogger(BrandController.class);
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "key",required = false) String key){
        logger.info("开始分页查询");
        PageResult<Brand> result=brandService.queryBrandByPage(page,rows,sortBy,desc,key);
        if (result==null||result.getItems().size()==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        logger.info("保存品牌信息");
        this.brandService.saveBrand(brand,cids);
        logger.info("保存品牌信息完成");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
