package egovframework.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageInfoVO {
    private int req_assoc;
    private int refund_limit;
    private int onecard_refund_limit;
}
