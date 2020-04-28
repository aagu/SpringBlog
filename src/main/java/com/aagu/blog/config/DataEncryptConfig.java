package com.aagu.blog.config;

import com.aagu.blog.util.AESUtil;
import com.aagu.blog.util.TextUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import javax.sql.DataSource;

@Configuration
public class DataEncryptConfig {

    @Value("${encKey}")
    private String encKey;

    private final Environment env;

    public DataEncryptConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() throws Exception{
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("spring.datasource.url"));
        config.setUsername(env.getProperty("spring.datasource.username"));
        config.setPassword(AESUtil.decrypt(env.getProperty("spring.datasource.password"), encKey));
        config.setConnectionTestQuery(env.getProperty("spring.datasource.hikari.connection-test-query"));
        config.setConnectionTimeout(2000L);
        config.setIdleTimeout(60000L);
        return new HikariDataSource(config);
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() throws Exception {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.defaultConfiguration();
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setDatabase(0);
        standaloneConfiguration.setHostName(env.getProperty("spring.redis.host"));
        String rawPwd = env.getProperty("spring.redis.password");
        if (TextUtil.notEmpty(rawPwd)) {
            standaloneConfiguration.setPassword(AESUtil.decrypt(rawPwd, encKey));
        }
        return new LettuceConnectionFactory(standaloneConfiguration, clientConfiguration);
    }
}
