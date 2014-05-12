package com.nd.im;


import com.nd.im.config.TableNames;
import java.util.Map;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author jianying9
 */
public class UpdateJUnitTest {

    public UpdateJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void moveDb() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(10);
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.59.99", 6379);
        String keyIndex = "KEY_" + TableNames.CUSTOMER;
        String keyPrefix = "I_Customer_";
        int dbIndex = TableNames.CUSTOMER_INDEX;
        Jedis jedis = jedisPool.getResource();
        jedis.select(0);
        long total = jedis.zcard(keyIndex);
        long start = 0;
        long end = 0;
        long pageSize = 500;
        Set<String> keySet;
        Map<String, String> entityMap;
        String rowKey;
        while (start < total) {
            System.out.println(start);
            end = start + pageSize - 1;
            if (end > total) {
                end = total - 1;
            }
            //读取
            jedis.select(0);
            keySet = jedis.zrevrange(keyIndex, start, end);
            for (String key : keySet) {
                rowKey = keyPrefix + key;
                jedis.select(0);
                entityMap = jedis.hgetAll(rowKey);
                if (entityMap.isEmpty()) {
//                    jedis.del(rowKey);
                    jedis.zrem(keyIndex, key);
                } else {
//                    jedis.del(rowKey);
                    //
                    jedis.select(dbIndex);
                    jedis.hmset(key, entityMap);
                }
            }
            start = start + pageSize;
        }
        jedis.close();
    }

}