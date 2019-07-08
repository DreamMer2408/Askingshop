package com.asking.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Stock
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/8 0:57
 * @Version 1.0
 **/
@Table(name = "tb_stock")
public class Stock {

    @Id
    private Long skuId;
    /**
     * 秒杀可用库存
     */
    private Integer seckillStock;
    /**
     * 已秒杀数量
     */
    private Integer seckillTotal;
    /**
     * 正常库存
     */
    private Long stock;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getSeckillStock() {
        return seckillStock;
    }

    public void setSeckillStock(Integer seckillStock) {
        this.seckillStock = seckillStock;
    }

    public Integer getSeckillTotal() {
        return seckillTotal;
    }

    public void setSeckillTotal(Integer seckillTotal) {
        this.seckillTotal = seckillTotal;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
