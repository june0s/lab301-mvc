package egovframework.lab.controller;

import egovframework.lab.entity.UserDto;
import egovframework.lab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/{id}/get.do")
    public String get(@PathVariable String id) {
        System.out.println("/api/user/{" + id + "} ==");
        final UserDto userDto = userRepository.select(id);
        System.out.println(userDto);

        return "ok";
    }

    @GetMapping("/test/{id}/test.do")
    public String test(@PathVariable String id) {
        final UserDto dto = new UserDto(id, "Hey", 32);
        userRepository.insert(id, dto);

        return "ok :D";
    }
}
