package com.asking.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Specification
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/7 0:55
 * @Version 1.0
 **/
@Table(name = "tb_specification")
public class Specification {
    @Id
    private Long categoryId;
    private String specifications;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
