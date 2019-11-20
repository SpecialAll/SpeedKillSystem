package com.zxh.speedsale_system.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @version 1.0
 * @Author ningque
 * @Date 2019/11/20
 *
 * redis配置引入
 */
//@Component
//@ConfigurationProperties(prefix = "spring.redis")
@Configuration
@Slf4j
public class JedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.poolMaxTotal}")
    private int poolMaxTotal;

    @Value("${spring.redis.poolMaxIdle}")
    private int poolMaxIdle;

    @Value("${spring.redis.poolMaxWait}")
    private int poolMaxWait;//秒

    @Bean
    public JedisPool redisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(poolMaxIdle);
        jedisPoolConfig.setMaxTotal(poolMaxTotal);
        jedisPoolConfig.setMaxWaitMillis(poolMaxWait);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);

        log.info("JedisPool注入成功");
        log.info("redis地址为：host-"+host+"-port-"+port);
        return jedisPool;
    }
}
