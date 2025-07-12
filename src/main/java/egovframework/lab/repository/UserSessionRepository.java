package egovframework.lab.repository;

import egovframework.lab.entity.UserSession;
import org.egovframe.rte.psl.reactive.redis.repository.EgovRedisRepository;
import org.egovframe.rte.ptl.reactive.annotation.EgovRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@EgovRepository
public class UserSessionRepository extends EgovRedisRepository<UserSession> {

    private String redisKey = "user:";

    public UserSessionRepository(
            @Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory connectionFactory,
            @Qualifier("userSessionSerializationContext") RedisSerializationContext serializationContext
    ) {
        super(connectionFactory, serializationContext);
    }

    public Flux<UserSession> selectAllUserSession() {
        return selectData(redisKey);
    }

//    public Flux<UserSession> selectSearchUserSessionUserSessionId(String keyword) {
//        Pattern regex = Pattern.compile(keyword);
//        return this.selectAllUserSession().filter(value -> regex.matcher(value.getUserSessionId()).find());
//    }
//
//    public Flux<UserSession> selectSearchUserSessionName(String keyword) {
//        Pattern regex = Pattern.compile(keyword);
//        return this.selectAllUserSession().filter(value -> regex.matcher(value.getName()).find());
//    }

    public Mono<UserSession> selectOneUserSession(String id) {
        Flux<UserSession> selected = selectData(redisKey + id);
//        Mono<UserSession> find = selected.filter(value -> value.getId().equals(id)).last();
        Mono<UserSession> find = selected.last();

        return find;
//        return selectData(redisKey).filter(value -> value.getId().equals(id)).last();
    }

    public Mono<Long> findIndex(UserSession UserSession) {
        return findIndex(redisKey, UserSession);
    }

    public Mono<UserSession> insertUserSession(String id, UserSession UserSession) {
        return insertData(redisKey + id, UserSession);
    }

    public Mono<UserSession> updateUserSession(Long idx, UserSession UserSession) {
        return updateData(redisKey, idx, UserSession);
    }

    public Mono<Boolean> deleteAllUserSession() {
        return deleteAllData(redisKey);
    }

    public Mono<Boolean> deleteUserSession(String id, UserSession UserSession) {
        return deleteData(redisKey + id, UserSession).hasElement();
    }

}
