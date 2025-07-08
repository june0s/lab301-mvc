package egovframework.lab.controller.dto;

import egovframework.lab.entity.Sample;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleDto {
    private String sampleId;
    private String name;
    private String description;
    private String useYn;
    private String regUser;

    public SampleDto(Sample sample) {
        this.sampleId = sample.getSampleId();
        this.name = sample.getName();
        this.description = sample.getDescription();
        this.useYn = sample.getUseYn();
        this.regUser = sample.getRegUser();
    }
}
