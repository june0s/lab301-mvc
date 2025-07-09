package egovframework.lab.util;

import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.output.IntegerOutput;
import io.lettuce.core.output.StatusOutput;
import io.lettuce.core.output.ValueOutput;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import org.springframework.stereotype.Component;

@Component
public class RedisJsonCommandExecutor {

    private final RedisAdvancedClusterCommands<String, String> redis;

    public RedisJsonCommandExecutor(RedisAdvancedClusterCommands<String, String> redis) {
        this.redis = redis;
    }

    public String jsonGet(String key, String path) {
        return redis.dispatch(
                CommandType.valueOf("JSON.GET"),
                new ValueOutput<>(StringCodec.UTF8),
                new CommandArgs<>(StringCodec.UTF8)
                        .add(key)
                        .add(path)
        );
    }

    public String jsonSet(String key, String path, String jsonValue) {
        return redis.dispatch(
                CommandType.valueOf("JSON.SET"),
                new StatusOutput<>(StringCodec.UTF8),
                new CommandArgs<>(StringCodec.UTF8)
                        .add(key)
                        .add(path)
                        .add(jsonValue)
        );
    }

//    public Long jsonDel(String key, String path) {
//        return redis.dispatch(
//                CommandType.valueOf("JSON.DEL"),
//                new IntegerOutput(),
//                new CommandArgs<>(StringCodec.UTF8)
//                        .add(key)
//                        .add(path)
//        );
//    }
}
