package com.asking.service;

import com.asking.item.pojo.Category;
import com.asking.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoryListByParentId(Long pid){
        logger.info("根据父节点Id：{}查询商品类目",pid);
        Category category=new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }

    /**
     * 查询当前数据库中最后一条数据
     * @return
     */
    public List<Category> queryLast(){
        List<Category> last=categoryMapper.selectLast();
        return last;
    }
    /**
     * 保存分类节点，并更新父节点
     * @param category
     */
    public void saveCategory(Category category){
        logger.info("开始添加分类节点");
        category.setId(null);
        int save=categoryMapper.insert(category);
        logger.info("结束添加分类节点：{}",save);

        //更新父节点
        Category parent=new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        categoryMapper.updateByPrimaryKeySelective(parent);
    }

    /**
     * 删除节点
     * @param id
     */
    public void deleteCategory(long id){
        logger.info("开始删除节点，id={}",id);

        Category category=categoryMapper.selectByPrimaryKey(id);
        logger.info("删除节点{},是父节点{}",category.getName(),category.getIsParent());
        if (category.getIsParent()){
            List<Category> list=new ArrayList<>();
            //查找所有叶子节点
            queryAllLeafNode(category,list);

            List<Category> list1=new ArrayList<>();
            //查找所有子节点
            queryAllNode(category,list1);
            //删除tb_category中的数据，使用list1
            for (Category c:list1){
                categoryMapper.delete(c);
            }
            //维护中间表
            for (Category c:list){
                categoryMapper.deleteByCategoryIdInCategoryBrand(c.getId());
            }
        }else{
            //查询此节点的父节点的孩子个数
            Example example=new Example(Category.class);
            example.createCriteria().andEqualTo("parentId",category.getParentId());
            List<Category> list=categoryMapper.selectByExample(example);
            if (list.size()!=1){
                categoryMapper.deleteByPrimaryKey(category.getId());
                //维护中间表
                categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }else {
                categoryMapper.deleteByPrimaryKey(category.getId());

                Category parent=new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                categoryMapper.updateByPrimaryKeySelective(parent);

                //维护中间表
                categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }
        }

    }

    /**
     * 查询本节点下所包含的所有叶子节点，用于维护tb_category_brand中间表
     * @param category
     * @param leafNode
     */
    private void queryAllLeafNode(Category category,List<Category> leafNode){
        if (!category.getIsParent()){
            leafNode.add(category);
        }
        Example example=new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list=categoryMapper.selectByExample(example);
        for (Category c:list){
            queryAllLeafNode(c,leafNode);
        }
    }

    /**
     * 查询本节点下的所有节点
     * @param category
     * @param node
     */
    private void queryAllNode(Category category,List<Category> node){
        node.add(category);
        Example example=new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list=categoryMapper.selectByExample(example);
        for (Category c:list){
            queryAllNode(c,node);
        }
    }

    /**
     * 根据brand_id查询分类信息
     * @param bid
     * @return
     */
    public List<Category> queryByBrandId(long bid){
        logger.info("查询商品id：{}",bid);
        return categoryMapper.queryByBrandId(bid);
    }
    /**
     * 更新节点
     * @param category
     */
    public void updateCategory(Category category){
        logger.info("修改节点为：{}",category.getName());
        categoryMapper.updateByPrimaryKeySelective(category);
        logger.info("节点已修改");
    }

    /**
     * 根据ids查询名字
     * @param asList
     * @return
     */
    public List<String> queryNameByIds(List<Long> asList){
        List<String> names=new ArrayList<>();
        if(asList!=null&&asList.size()!=0){
            for (long id:asList) {
                names.add(categoryMapper.queryNameById(id));
            }
        }
        return names;
    }
}
