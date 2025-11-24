package egovframework.lab.controller;

import com.google.gson.Gson;
import egovframework.lab.controller.dto.GetUserDto;
import egovframework.lab.entity.UserDto;
import egovframework.lab.repository.UserRepository;
import egovframework.lab.service.UserService;
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
    private final UserService userService;

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

    // 외부 Rest api 서버에서 데이터 가져와서 데이터 반환
    @GetMapping("/users/get.do")
    public String getUsersFromTestServer() {
        GetUserDto users = userService.getUsers();
        System.out.println(users);

        return new Gson().toJson(users);
    }

}
