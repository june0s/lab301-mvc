package egovframework.lab.config;

import egovframework.lab.entity.Ids;
import egovframework.lab.entity.Sample;
import egovframework.lab.entity.UserSession;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.egovframe.rte.psl.reactive.redis.connect.EgovRedisConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:application.yml")
public class EgovRedisConfig {

//    @Value("${spring.redis.host}")
    private String host = "127.0.0.1";

//    @Value("${spring.redis.port}")
    private int port = 6379;
    private String nodes = "redis-cluster-0.redis-cluster.redis.svc.cluster.local:6379, " +
            "redis-cluster-1.redis-cluster.redis.svc.cluster.local:6379, " +
            "redis-cluster-2.redis-cluster.redis.svc.cluster.local:6379";
    private String password = "redis@kra";

    @Bean(name="reactiveRedisConnectionFactory")
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        EgovRedisConfiguration egovRedisConfiguration = new EgovRedisConfiguration(this.host, this.port);
        System.out.println("## REDIS host = " + host + ", port = " + port);
        return egovRedisConfiguration.reactiveRedisConnectionFactory();

        // 3. redis cluster 모드
//        System.out.println("nodes = " + nodes);
//        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(
//                Arrays.asList(nodes)
//        );
//        clusterConfiguration.setPassword(password);

        // (1) Redis Cluster 설정
//        int maxRedirects = 3;
//        List<RedisNode> redisNodes = new ArrayList<>();
//        redisNodes.add(new RedisNode("redis-cluster-0.redis-cluster-headless.redis.svc.cluster.local", 6379));
//        redisNodes.add(new RedisNode("redis-cluster-1.redis-cluster-headless.redis.svc.cluster.local", 6379));
//        redisNodes.add(new RedisNode("redis-cluster-2.redis-cluster-headless.redis.svc.cluster.local", 6379));
//
//        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
//        clusterConfiguration.setClusterNodes(redisNodes);
//        clusterConfiguration.setMaxRedirects(maxRedirects);
//        clusterConfiguration.setPassword(password);
//
//        // (2) Socket 옵션
//        SocketOptions socketOptions = SocketOptions.builder()
//                .connectTimeout(Duration.ofMillis(100L))
//                .keepAlive(true)
//                .build();
//
//        // (3) Cluster topology refresh 옵션
//        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
//                .dynamicRefreshSources(true)
//                .enableAdaptiveRefreshTrigger()
//                .enablePeriodicRefresh(Duration.ofMinutes(30L))
//                .build();
//
//        // (4) Cluster Client 옵션
//        ClusterClientOptions clientOptions = ClusterClientOptions.builder()
//                .topologyRefreshOptions(clusterTopologyRefreshOptions)
//                .socketOptions(socketOptions)
//                .build();
//
//        // (5) Lettuce Client 옵션
//        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
//                .clientOptions(clientOptions)
//                .commandTimeout(Duration.ofMillis(3000L))
//                .build();
//
//        return new LettuceConnectionFactory(clusterConfiguration, clientConfiguration);
    }

    @Bean(name="idsSerializationContext")
    public RedisSerializationContext<String, Ids> idsReactiveRedisTemplate() {
        Jackson2JsonRedisSerializer<Ids> serializer = new Jackson2JsonRedisSerializer<>(Ids.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Ids> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        return builder.value(serializer).hashKey(serializer).hashValue(serializer).build();
    }

    @Bean(name="sampleSerializationContext")
    public RedisSerializationContext<String, Sample> sampleReactiveRedisTemplate() {
        Jackson2JsonRedisSerializer<Sample> serializer = new Jackson2JsonRedisSerializer<>(Sample.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Sample> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        return builder.value(serializer).hashKey(serializer).hashValue(serializer).build();
    }

    @Bean(name="userSessionSerializationContext")
    public RedisSerializationContext<String, UserSession> userSessionReactiveRedisTemplate() {
        Jackson2JsonRedisSerializer<UserSession> serializer = new Jackson2JsonRedisSerializer<>(UserSession.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, UserSession> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        return builder.value(serializer).hashKey(serializer).hashValue(serializer).build();
    }

}
