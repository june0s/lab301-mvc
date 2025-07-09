package egovframework.lab.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

// 객체 <-> JSON 직렬화 자동 처리 클래스
@Component
public class RedisJsonTemplate {

    private final ObjectMapper objectMapper;
    private final RedisJsonCommandExecutor executor;

    public RedisJsonTemplate(ObjectMapper objectMapper, RedisJsonCommandExecutor executor) {
        this.objectMapper = objectMapper;
        this.executor = executor;
    }

    public <T> void set(String key, T value) {
        try {
            String json = objectMapper.writeValueAsString(value);
            executor.jsonSet(key, "$", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            String json = executor.jsonGet(key, "$");
            if (json == null || json.equals("null")) return null;

            // RedisJSON은 배열처럼 반환: ["{...}"] or {"key":"value"}
            if (json.startsWith("[")) {
                JsonNode node = objectMapper.readTree(json);
                return objectMapper.treeToValue(node.get(0), clazz);
            } else {
                return objectMapper.readValue(json, clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON from Redis", e);
        }
    }

//    public void delete(String key) {
//        executor.jsonDel(key, "$");
//    }
}
