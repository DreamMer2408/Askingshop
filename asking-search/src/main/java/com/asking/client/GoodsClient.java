package com.asking.client;

import com.asking.item.api.GoodsApi;
import com.asking.item.pojo.Sku;
import com.asking.item.pojo.SpuBo;
import com.asking.item.pojo.SpuDetail;
import com.askingshop.common.pojo.PageResult;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {

}
