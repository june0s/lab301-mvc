package egovframework.lab.service;

import egovframework.lab.controller.dto.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ApiService<GetUserDto> apiService;
    private final static String SERVER = "http://localhost:3100";

    public GetUserDto getUsers() {
        String url = SERVER +  "/users/";
        ResponseEntity<GetUserDto> result = apiService.get(url, null, GetUserDto.class);
        System.out.println("+ code = " + result.getStatusCode());
        System.out.println("+ body = " + result.getBody());

        return result.getBody();
    }
}
