package com.Leo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Auther: Leo
 * @Date: 2018/11/7
 * @Description:
 */
public class JedisUtil {

    private final JedisPool jedisPool;

    private JedisUtil(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    private Jedis getResource(){
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try {
            jedis.set(key,value);
            return value;
        }finally {
            jedis.close();

        }
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();
        try {
            jedis.expire(key,i);
        }finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key){
        Jedis jedis = getResource();
        try {
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis jedis = getResource();
        try {
            return jedis.keys((shiro_session_prefix + "*").getBytes());
        }finally {
            jedis.close();
        }
    }
}
