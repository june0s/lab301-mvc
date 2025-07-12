package egovframework.lab.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("usersession")
@Builder
@ToString
public class UserSession {
    @Id
    private String acctNum;
    private String osCd;
    private String devUuid;
    private int moViewCnt;
    private MileageInfoVO mileageInfoVO;
    private LoginVO loginVO;
}
