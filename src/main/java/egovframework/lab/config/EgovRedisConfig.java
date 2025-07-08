package egovframework.lab.config;

import egovframework.lab.entity.Ids;
import egovframework.lab.entity.Sample;
import org.egovframe.rte.psl.reactive.redis.connect.EgovRedisConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

@Configuration
@PropertySource("classpath:application.yml")
public class EgovRedisConfig {

//    @Value("${spring.redis.host}")
    private String host = "redis"; //"127.0.0.1";

//    @Value("${spring.redis.port}")
    private int port = 6379;
    private String nodes = "redis-cluster-0.redis-cluster.redis.svc.cluster.local:6379, " +
            "redis-cluster-1.redis-cluster.redis.svc.cluster.local:6379, " +
            "redis-cluster-2.redis-cluster.redis.svc.cluster.local:6379";
    private String password = "redis@kra";

    @Bean(name="reactiveRedisConnectionFactory")
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        EgovRedisConfiguration egovRedisConfiguration = new EgovRedisConfiguration(this.host, this.port);
//        System.out.println("## REDIS host = " + host + ", port = " + port);
//        return egovRedisConfiguration.reactiveRedisConnectionFactory();

        // 3. redis cluster 모드
        System.out.println("nodes = " + nodes);
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(
                Arrays.asList(
                        nodes
                )
        );
        clusterConfiguration.setPassword(password);

        return new LettuceConnectionFactory(clusterConfiguration);
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

}
