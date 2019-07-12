package com.asking.client;

import com.asking.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface CategoryClient  extends CategoryApi {
}
