package egovframework.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private String acct_num;
//    private boolean isShowEventPopup;
    private String showEventPopup;
    private String[] cnt_limit;
    private UseApply useApply;
    private AccountUseApplyVO accountUseApplyVO;

}
