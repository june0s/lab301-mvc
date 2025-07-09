package egovframework.lab.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class RedisLettuceConfig {

    @Bean
    public RedisClusterClient redisClusterClient() {
//        RedisClusterClient clusterClient = RedisClusterClient.create(Arrays.asList(
//                RedisURI.create("redis://redis-cluster-0.redis-cluster-headless.redis:6379"),
//                RedisURI.create("redis://redis-cluster-1.redis-cluster-headless.redis:6379"),
//                RedisURI.create("redis://redis-cluster-2.redis-cluster-headless.redis:6379")
//        ));
        String password = "redis@kra";

        return RedisClusterClient.create(Arrays.asList(
                RedisURI.builder().redis("redis-cluster-0.redis-cluster-headless.redis", 6379)
                        .withPassword(password)
                        .build(),
                RedisURI.builder().redis("redis-cluster-1.redis-cluster-headless.redis", 6379)
                        .withPassword(password)
                        .build(),
                RedisURI.builder().redis("redis-cluster-2.redis-cluster-headless.redis", 6379)
                        .withPassword(password)
                        .build()
        ));
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisClusterConnection<String, String> connection(RedisClusterClient client) {
        System.out.println(">> Redis connection() Bean 생성됨!");
        return client.connect();
    }

    @Bean
    public RedisAdvancedClusterCommands<String, String> redisCommands(StatefulRedisClusterConnection<String, String> connection) {
        System.out.println(">> Redis command() 호출됨!");
        return connection.sync();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
