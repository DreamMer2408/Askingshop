package com.asking.controller;

import com.asking.item.pojo.Brand;
import com.asking.item.pojo.Category;
import com.asking.service.BrandService;
import com.asking.common.pojo.PageResult;
import com.asking.service.CategoryService;
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

    @Autowired
    private CategoryService categoryService;

    private static final Logger logger=LoggerFactory.getLogger(BrandController.class);

    /**
     * 分页查询品牌
     * @param page      //当前页
     * @param rows      //每页大小
     * @param sortBy    //排序字段
     * @param desc      //是否降序
     * @param key       //搜索关键词
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "key",required = false) String key){
        logger.info("开始品牌分页查询");
        PageResult<Brand> result=brandService.queryBrandByPage(page,rows,sortBy,desc,key);
        if (result==null||result.getItems().size()==0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 添加品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        logger.info("保存品牌信息");
        this.brandService.saveBrand(brand,cids);
        logger.info("保存品牌信息完成");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 修改品牌信息
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids")List<Long> cids){
        logger.info("保存品牌信息的修改,id:{}",brand.getId());
        brandService.updateBrand(brand,cids);
        logger.info("品牌信息修改完成");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 删除品牌
     * @param bidString
     * @return
     */
    @DeleteMapping("/bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid")String bidString){
        logger.info("删除品牌id为{}的品牌信息",bidString);
        String separator="-";
        if (bidString.contains(separator)){
            String[] bids=bidString.split(separator);
            for (String bid:bids){
                brandService.deleteBrand(Long.parseLong(bid));
            }
        }else {
            brandService.deleteBrand(Long.parseLong(bidString));
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 根据分类id查询brand
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategoryId(@PathVariable("cid")Long cid){
        List<Brand> brandList=brandService.queryBrandByCid(cid);
        if(brandList==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(brandList);
    }
}
