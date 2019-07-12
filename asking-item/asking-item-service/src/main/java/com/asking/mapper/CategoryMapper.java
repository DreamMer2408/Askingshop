package com.asking.mapper;

import com.asking.item.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {

    @Select("select name from tb_category where id=#{id}")
    String queryNameById(long id);

    @Delete("delete from tb_category_brand where category_id=#{cid}")
    void deleteByCategoryIdInCategoryBrand(@Param("cid")Long cid);

    @Select("select * from tb_category where id=(select max(id) from tb_category)")
    List<Category> selectLast();
}
