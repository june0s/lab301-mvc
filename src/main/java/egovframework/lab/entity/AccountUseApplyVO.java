package egovframework.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUseApplyVO {
    private long balan;
    public String acct_num;
    public byte acct_type;
}
