package egovframework.lab.controller;

import egovframework.lab.controller.dto.SampleDto;
import egovframework.lab.entity.KValue;
import egovframework.lab.entity.Sample;
import egovframework.lab.entity.SampleData;
import egovframework.lab.repository.KValueRepository;
import egovframework.lab.repository.SampleRepository;
import egovframework.lab.util.RedisJsonTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.json.JsonSetParams;
import redis.clients.jedis.providers.PooledConnectionProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SampleController {

    private final SampleRepository sampleRepository;
    private final KValueRepository kValueRepository;
    private final RedisJsonTemplate redisJsonTemplate;

    @GetMapping("/sample/{sampleId}/get.do")
    public String get(@PathVariable String sampleId) {
        System.out.println("get sample! id = " + sampleId);

        Sample sample = sampleRepository.selectOneSample(sampleId).block();
        System.out.println("id = " + sample.getSampleId());
        SampleDto sampleDto = new SampleDto(sample);
        System.out.println(sampleDto.toString());
        return "ok";
    }

    @GetMapping("/value")
    public String value() {
        System.out.println("/api/value ==");
        KValue kValue = kValueRepository.selectAllKValues().blockFirst();
        System.out.println("result = " + kValue.getValue());

        return "ok";
    }

    @GetMapping("/sample/{sampleId}/json.do")
    public String json(@PathVariable String sampleId) {
        System.out.println("get sample! id = " + sampleId);

        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("redis-cluster-0.redis-cluster-headless.redis.svc.cluster.local", 6379));
        nodes.add(new HostAndPort("redis-cluster-1.redis-cluster-headless.redis.svc.cluster.local", 6379));
        nodes.add(new HostAndPort("redis-cluster-2.redis-cluster-headless.redis.svc.cluster.local", 6379));

        DefaultJedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
                .password("redis@kra")
                .timeoutMillis(2000)
                .build();
        try (PooledConnectionProvider provider = new PooledConnectionProvider((HostAndPort) nodes, clientConfig)) {
            UnifiedJedis client = new UnifiedJedis(provider);

            String json = "{\"name\":\"Alice\",\"age\":30}";
//            String res = client.jsonSet("user:1", JsonSetParams.jsonSetParams(), json);
            String res = client.jsonSet("user:1", json, JsonSetParams.jsonSetParams());
            System.out.println("JSON.SET result: " + res);

//            // 특정 경로 변경
//            String res2 = client.jsonSet("user:1", JsonSetParams.jsonSetParams(), "$.age", "31");
//            System.out.println("JSON.SET age: " + res2);

            // JSON.GET 예시
            String fetched = client.jsonGet("user:1", String.class);
            System.out.println("JSON.GET result: " + fetched);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "ok";
    }

    @PostMapping("/save.do")
    public String save() {
        SampleData data = new SampleData();
        data.id = UUID.randomUUID().toString();
        data.name = "Redis JSON";
        data.active = true;

        redisJsonTemplate.set("sample:1", data);
        return "Saved!";
    }

    @GetMapping("/load.do")
    public SampleData load() {
        return redisJsonTemplate.get("sample:1", SampleData.class);
    }


}
