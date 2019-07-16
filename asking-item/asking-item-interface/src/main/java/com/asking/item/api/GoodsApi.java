package com.asking.item.api;

import com.asking.item.pojo.Sku;
import com.asking.item.bo.SpuBo;
import com.asking.item.pojo.SpuDetail;
import com.asking.common.pojo.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：wang
 * @描述：商品服务接口
 */
@RequestMapping("goods")
public interface GoodsApi {
    /**
     * 分页查询商品
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @RequestMapping("/spu/page")
    PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") Boolean desc,
            @RequestParam(value = "saleable",defaultValue = "true") Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    );
    /**
     * 根据spu商品id 查询详情
     * @param id
     * @return
     */
    @RequestMapping("/spu/detail/{id}")
    SpuDetail querySpuDetailById(@PathVariable("id")Long id);
    /**
     * 根据spu的id查询sku
     * @param id
     * @return
     */
    @RequestMapping("/sku/list/{id}")
    List<Sku>querySkuBySpuId (@PathVariable("id") Long id);
}
