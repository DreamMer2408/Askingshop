package com.asking.controller;

import com.asking.item.pojo.Specification;
import com.asking.service.SpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SpecificationController
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/7 0:59
 * @Version 1.0
 **/
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    private static final Logger logger= LoggerFactory.getLogger(SpecificationController.class);

    /**
     * 查询商品分类对应的规格参数模板
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<String> querySpecificationByCategoryId(@PathVariable("id")Long id){
        logger.info("开始查询规格参数");
        Specification spec=specificationService.queryById(id);
        logger.info("结束查询规格参数");
        if (spec==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spec.getSpecifications());
    }

    /**
     * 保存一个规格参数模板
     * @param specification
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveSpecification(Specification specification){
        int i=specificationService.saveSpecification(specification);
        logger.info(i+"");
        return ResponseEntity.ok().build();
    }

    /**
     * 修改
     * @param specification
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateSpecification(Specification specification){
        specificationService.updateSpecification(specification);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSpecification(@PathVariable("id")Long id){
        Specification specification=new Specification();
        specification.setCategoryId(id);
        specificationService.deleteSpecification(specification);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
