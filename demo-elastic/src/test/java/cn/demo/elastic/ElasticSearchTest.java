package cn.demo.elastic;

import cn.demo.elastic.pojo.Item;
import cn.demo.elastic.repository.ItemRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ElasticSearchTest
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/15 13:00
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testIndex(){
        elasticsearchTemplate.createIndex(Item.class);
        elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void index(){
        Item item = new Item(1L, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

    @Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        itemRepository.saveAll(list);
    }

    @Test
    public void findTest(){
        Iterable<Item> items=itemRepository.findAll(Sort.by(Sort.Direction.DESC,"price"));
        items.forEach(item -> System.out.println(item));
    }

    @Test
    public void testQuery(){
        MatchQueryBuilder queryBuilder= QueryBuilders.matchQuery("title","小米");
        Iterable<Item> items=itemRepository.search(queryBuilder);
        items.forEach(System.out::println);
    }
}
