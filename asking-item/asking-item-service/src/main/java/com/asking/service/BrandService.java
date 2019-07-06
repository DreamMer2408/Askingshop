package com.asking.service;

import com.asking.item.pojo.Brand;
import com.asking.mapper.BrandMapper;
import com.askingshop.common.pojo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Transient;
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

    public PageResult<Brand> queryBrandByPage(Integer page,Integer rows,String sortBy,Boolean desc,String key) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Brand.class);
        if (!StringUtils.isNullOrEmpty(key)) {
            example.createCriteria().andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        if (!StringUtils.isNullOrEmpty(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids){
        this.brandMapper.insertSelective(brand);
        for (Long cid:cids){
            this.brandMapper.insertCategoryBrand(cid,brand.getId());
        }
    }
}
