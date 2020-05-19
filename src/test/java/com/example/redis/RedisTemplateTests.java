package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateTests {

    /*
        redisTemplate.opsForValue();//操作字符串
        redisTemplate.opsForHash();//操作hash
        redisTemplate.opsForList();//操作list
        redisTemplate.opsForSet();//操作set
        redisTemplate.opsForZSet();//操作有序set
        https://www.cnblogs.com/EasonJim/p/7803067.html
    */
    @Autowired
    private RedisTemplate redisTemplate;


    //String
    @Test
    public void testSring() {
        //增
        redisTemplate.opsForValue().set("name", "tom");
        //查
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);


        //新增并设置有效时间
        redisTemplate.opsForValue().set("name", "tom", 10, TimeUnit.SECONDS);
        redisTemplate.opsForValue().get("name");//由于设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null

        //删
        redisTemplate.delete("name");
        System.out.println(redisTemplate.opsForValue().get("name"));


        //批量增加
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("multi11", "multi11");
        maps.put("multi22", "multi22");
        maps.put("multi33", "multi33");
        Map<String, String> maps2 = new HashMap<String, String>();
        maps2.put("multi1", "multi1");
        maps2.put("multi2", "multi2");
        maps2.put("multi3", "multi3");
        System.out.println(redisTemplate.opsForValue().multiSetIfAbsent(maps)); //true
        System.out.println(redisTemplate.opsForValue().multiSetIfAbsent(maps2));//false


        //设置键的字符串值并返回其旧值
        redisTemplate.opsForValue().set("getSetTest", "test");
        System.out.println(redisTemplate.opsForValue().getAndSet("getSetTest", "test2")); //输出test

        //为多个键分别取出它们的值
        Map<String, String> map = new HashMap<String, String>();
        map.put("multi1", "multi1");
        map.put("multi2", "multi2");
        map.put("multi3", "multi3");
        redisTemplate.opsForValue().multiSet(map);

        List<String> keys = new ArrayList<String>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
        System.out.println(redisTemplate.opsForValue().multiGet(keys));

        /*
        append Integer append(K key, String value);
        如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
         */
        redisTemplate.opsForValue().append("appendTest", "Hello");
        System.out.println(redisTemplate.opsForValue().get("appendTest"));//Hello
        redisTemplate.opsForValue().append("appendTest", "world");
        System.out.println(redisTemplate.opsForValue().get("appendTest"));//Helloworld

    }

    /*
    List
     */
    @Test
    public void testList() {
        //增
        List<String> list1 = new ArrayList<String>();
        list1.add("a1");
        list1.add("a2");
        list1.add("a3");

        List<String> list2 = new ArrayList<String>();
        list2.add("b1");
        list2.add("b2");
        list2.add("b3");
        redisTemplate.opsForList().leftPush("listkey1", list1);
        redisTemplate.opsForList().rightPush("listkey2", list2);

        //取
        List listkey1 = redisTemplate.opsForList().range("listkey1", 0, -1);
        System.out.println(listkey1.toString());



        /*
        移除
         不管是leftPush还是rightPush都可以用leftPop或者rightPoP任意一种获取到其中的值，
        不过就是获取的遍历方向不一样,其实就是遍历方向不同，所以效率也不同。
        所以最好leftPush用leftPoP遍历，rightPush用rightPoP遍历
         */
        List<String> resultList1 = (List<String>) redisTemplate.opsForList().leftPop("listkey1");
        List<String> resultList2 = (List<String>) redisTemplate.opsForList().rightPop("listkey2");
        System.out.println("resultList1:" + resultList1);
        System.out.println("resultList2:" + resultList2);
    }

    /*
    set
     */
    @Test
    public void testSet() {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        set.add("set1", "22");
        set.add("set1", "33");
        set.add("set1", "44");
        Set<String> resultSet = redisTemplate.opsForSet().members("set1");
    }


    /*
    hash
     */
    @Test
    public void testHash() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");
        redisTemplate.opsForHash().putAll("map1", map);
        Map<String, String> resultMap = redisTemplate.opsForHash().entries("map1");
        List<String> reslutMapList = redisTemplate.opsForHash().values("map1");
        Set<String> resultMapSet = redisTemplate.opsForHash().keys("map1");
        String value = (String) redisTemplate.opsForHash().get("map1", "key1");
        System.out.println("value:" + value);
        System.out.println("resultMapSet:" + resultMapSet);
        System.out.println("resultMap:" + resultMap);
        System.out.println("resulreslutMapListtMap:" + reslutMapList);
    }

    /*
    zset
    */
    @Test
    public void testZSet() {
        //向集合中插入元素，并设置分数
        redisTemplate.opsForZSet().add("ranking-list", "p1", 2.1);

        //向集合中插入多个元素
        DefaultTypedTuple<String> tuple1 = new DefaultTypedTuple<String>("p2", 1.1);
        DefaultTypedTuple<String> tuple2 = new DefaultTypedTuple<String>("p3", 3.1);
        redisTemplate.opsForZSet().add("ranking-list", new HashSet<>(Arrays.asList(tuple1, tuple2)));

        //打印
        printZSet("ranking-list");
    }

    private void printZSet(String key) {
        //按照排名先后(从小到大)打印指定区间内的元素, -1为打印全部
        Set<String> range = redisTemplate.opsForZSet().range(key, 0, -1);
        //reverseRange 从大到小
        System.out.println(range);
    }


}
