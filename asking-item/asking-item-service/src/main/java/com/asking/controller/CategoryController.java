package com.asking.controller;

import com.asking.item.pojo.Category;
import com.asking.service.CategoryService;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点ID查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByParentId(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        try {
            if (pid==null||pid<0){
                //400：参数不合法
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            List<Category> categories=categoryService.queryCategoryListByParentId(pid);
            if (CollectionUtils.isEmpty(categories)){
                //404：资源服务器未找到
                return ResponseEntity.notFound().build();
            }
            //200：查询成功
            return ResponseEntity.ok(categories);
        }catch (Exception e){
            e.printStackTrace();
        }
        //500:服务器内部错误
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @GetMapping("/names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids")List<Long> ids){
        List<String> list=categoryService.queryNameByIds(ids);
        if (list==null||list.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }
}
