package egovframework.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UseApply {
    private String acct_num;
    private long pay_holding_amt;
    private int[][] outpark_race_sell_amt;
}
