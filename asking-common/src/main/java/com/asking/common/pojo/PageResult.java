package com.asking.common.pojo;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description 分页
 * @Author wangs
 * @Date 2019/7/4 20:43
 * @Version 1.0
 **/
public class PageResult<T> {
    private Long total;     //总条数
    private Long totalPage; //总页数
    private List<T> items;  //当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
