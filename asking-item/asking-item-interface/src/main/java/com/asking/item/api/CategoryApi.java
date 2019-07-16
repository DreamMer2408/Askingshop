package com.asking.item.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author:wang
 * @描述：商品分类服务接口
 */
@RequestMapping("category")
public interface CategoryApi {
    /**
     * 根据ids，查询分类名称
     * @param ids
     * @return
     */
    @RequestMapping("names")
    ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids")List<Long> ids);

}
