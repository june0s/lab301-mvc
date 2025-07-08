package egovframework.lab.repository;

import egovframework.lab.entity.KValue;
import org.egovframe.rte.psl.reactive.redis.repository.EgovRedisRepository;
import org.egovframe.rte.ptl.reactive.annotation.EgovRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EgovRepository
public class KValueRepository extends EgovRedisRepository<KValue> {

    private String redisKey = "key-1";

    public KValueRepository(
            @Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory connectionFactory,
            @Qualifier("idsSerializationContext") RedisSerializationContext serializationContext
    ) {
        super(connectionFactory, serializationContext);
    }

    public Flux<KValue> selectAllKValues() {
        return selectData(redisKey);
    }


    public Mono<Long> findIndex(KValue kValue) {
        return findIndex(redisKey, kValue);
    }
//
//    public Mono<Long> countIds() {
//        return countData(redisKey);
//    }
//
//    public Mono<Ids> insertIds(Ids ids) {
//        return insertData(redisKey, ids);
//    }
//
//    public Mono<Ids> updateIds(Long idx, Ids ids) {
//        return updateData(redisKey, idx, ids);
//    }
//
//    public Mono<Boolean> deleteAllIds() {
//        return deleteAllData(redisKey);
//    }
//
//    public Mono<Boolean> deleteIds(Ids ids) {
//        return deleteData(redisKey, ids);
//    }

}
