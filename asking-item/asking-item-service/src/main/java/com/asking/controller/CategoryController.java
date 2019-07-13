package com.asking.controller;

import com.asking.item.pojo.Category;
import com.asking.service.CategoryService;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.tree.VoidDescriptor;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);


    /**
     * 根据父节点ID查询商品类目
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        if (pid==-1){
            List<Category> last=categoryService.queryLast();
            return ResponseEntity.ok(last);
        }else{
            List<Category> list=categoryService.queryCategoryListByParentId(pid);
            if (list==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 添加节点
     * @param category
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveCategory(Category category){
        logger.info("添加新节点：{}",category.getName());
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新
     * @param category
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(Category category){
        logger.info("保存修改,id:{}",category.getId());
        categoryService.updateCategory(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 删除节点
     * @param id
     * @return
     */
    @DeleteMapping("/cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid")long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids")List<Long> ids){
        List<String> list=categoryService.queryNameByIds(ids);
        if (list==null||list.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }
    /**
     * 商品分类信息的回显
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid")long bid){
        logger.info("开始商品信息查询");
        List<Category> list=categoryService.queryByBrandId(bid);
        if (list==null||list.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }
}
