package com.xwbing.redis;

import com.xwbing.util.CommonConstant;
import com.xwbing.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 说明:封装redis 缓存服务器服务接口
 * 项目名称: spring-demo
 * 创建日期: 2016年9月27日 下午1:25:47
 * 作者: xiangwb
 */
@Component("redisService")
public class RedisService {
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    private static String prefix;
    private static Jedis jedis;

    private RedisService() {
        if (StringUtils.isEmpty(prefix)) {
            prefix = PropertiesUtil.getValueByKey("customerRedisCode", CommonConstant.SYSTEM);
        }
    }

    /**
     * 获取一个jedis 客户端
     *
     * @return
     */
    private Jedis getJedis() {
        if (jedis == null) {
            return jedisConnectionFactory.getShardInfo().createResource();
        }
        return jedis;
    }

    /**
     * 通过key删除（字节）
     *
     * @param key
     */
    public void del(byte[] key) {
        this.getJedis().del(key);
    }

    /**
     * 通过key删除
     *
     * @param key
     */
    public void del(String key) {
        key = prefix + key;
        this.getJedis().del(key);
    }

    /**
     * 功能描述：初始化的时候删除
     *
     * @param key
     */
    public void delInit(String key) {
        this.getJedis().del(key);
    }


    /**
     * 功能描述：刷新缓存
     */
    public void flushAll() {
        this.getJedis().flushAll();
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte[] key, byte[] value, int liveTime) {

        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, int liveTime) {
        key = prefix + key;
        this.getJedis().setex(key, liveTime, value);
    }

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        key = prefix + key;
        this.getJedis().set(key, value);
    }

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        this.getJedis().set(key, value);
    }

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    public String get(String key) {
        key = prefix + key;
        String value = this.getJedis().get(key);
        return value;
    }

    /**
     * 获取redis value (byte [] )(反序列化)
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        return this.getJedis().get(key);
    }

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return this.getJedis().keys(pattern);
    }

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        key = prefix + key;
        return this.getJedis().exists(key);
    }

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    public String flushDB() {
        return this.getJedis().flushDB();
    }

    /**
     * 查看redis里有多少数据
     */
    public long dbSize() {
        return this.getJedis().dbSize();
    }

    /**
     * 检查是否连接成功
     *
     * @return
     */
    public String ping() {
        return this.getJedis().ping();
    }


}
