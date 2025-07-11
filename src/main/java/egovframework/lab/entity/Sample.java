package egovframework.lab.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("sample")
@Builder
public class Sample {

    @Id
    private String id;
    @Indexed
    private String sampleId;
    @Indexed
    private String name;
    private String description;
    private String useYn;
    private String regUser;

}
