package com.asking.test;

import com.asking.AskingSearchService;
import com.asking.client.CategoryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName CategoryClientTest
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/10 23:37
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AskingSearchService.class)
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void testQueryCategories(){
        List<String> names=categoryClient.queryNameByIds(Arrays.asList(1L,2L,3L)).getBody();
        names.forEach(System.out::println);
    }
}
