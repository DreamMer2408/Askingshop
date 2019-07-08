package com.asking.service;

import com.asking.item.pojo.Specification;
import com.asking.mapper.SpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SpecificationService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/7 0:59
 * @Version 1.0
 **/
@Service
public class SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    public Specification queryById(Long id){
        return specificationMapper.selectByPrimaryKey(id);
    }

    public int saveSpecification(Specification specification){
        return specificationMapper.insert(specification);
    }

    public int updateSpecification(Specification specification){
        return specificationMapper.updateByPrimaryKeySelective(specification);
    }

    public int deleteSpecification(Specification specification){
        return specificationMapper.delete(specification);
    }
}
