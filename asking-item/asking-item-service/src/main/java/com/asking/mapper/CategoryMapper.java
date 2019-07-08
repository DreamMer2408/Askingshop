package com.asking.mapper;

import com.asking.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {

    @Select("select name from tb_category where id=#{id}")
    String queryNameById(long id);
}
