package egovframework.lab.controller;

import egovframework.lab.controller.dto.SampleDto;
import egovframework.lab.entity.KValue;
import egovframework.lab.entity.Sample;
import egovframework.lab.repository.KValueRepository;
import egovframework.lab.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SampleController {

    private final SampleRepository sampleRepository;
    private final KValueRepository kValueRepository;

    @GetMapping("/sample/{sampleId}/get.do")
    public String get(@PathVariable String sampleId) {
        System.out.println("get sample! id = " + sampleId);

        Sample sample = sampleRepository.selectOneSample(sampleId).block();
        System.out.println("id = " + sample.getSampleId());
        SampleDto sampleDto = new SampleDto(sample);
        System.out.println(sampleDto.toString());
        return "ok";
    }

    @GetMapping("/value")
    public String value() {
        System.out.println("/api/value ==");
        KValue kValue = kValueRepository.selectAllKValues().blockFirst();
        System.out.println("result = " + kValue.getValue());

        return "ok";
    }
}
