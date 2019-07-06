package com.asking.service;

import com.asking.item.pojo.Category;
import com.asking.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoryListByParentId(Long pid){
        Category category=new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }
}
