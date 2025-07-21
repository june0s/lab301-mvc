package egovframework.lab.repository;

import egovframework.lab.entity.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final RedisTemplate<String, UserDto> redisTemplate;
    private final String redisKey = "users";

    public void insert(String id, UserDto userDto) {
        System.out.println("[REDIS] insert id = " + id + " userDto = " + userDto);
        redisTemplate.opsForHash().put(redisKey, id, userDto);
    }

    public UserDto select(String id) {
        final UserDto userDto = (UserDto) redisTemplate.opsForHash().get(redisKey, id);
        System.out.println("[REDIS] select id = " + id + " userDto = " + userDto);
        return userDto;
    }
}
