package com.asking.item.bo;

import com.asking.item.pojo.Sku;
import com.asking.item.pojo.Spu;
import com.asking.item.pojo.SpuDetail;

import javax.persistence.Transient;
import java.util.List;

/**
 * @ClassName SpuBo
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/7 19:15
 * @Version 1.0
 **/
public class SpuBo extends Spu {
    @Transient
    private String cname;   //商品分类名称
    @Transient
    private String bname;   //品牌名称

    @Transient
    private SpuDetail spuDetail;    //商品详情

    @Transient
    private List<Sku> skus;         //Sku列表
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }
}
