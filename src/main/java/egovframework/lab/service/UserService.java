package egovframework.lab.service;

import egovframework.lab.controller.dto.GetUserDto;
import egovframework.lab.exception.ApiClientException;
import egovframework.lab.exception.ApiServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ApiService<GetUserDto> apiService;
    private final static String SERVER = "http://localhost:3000";

    public GetUserDto getUsers() {
        String url = SERVER + "/users/";
        ResponseEntity<GetUserDto> result = null;
        try {
            result = apiService.get(url, null, GetUserDto.class);
            System.out.println("+ code = " + result.getStatusCode());
            System.out.println("+ body = " + result.getBody());
        } catch (ApiClientException e) {
            System.out.printf("client error code = " + e.getStatusCode() + ", msg = " + e.getMessage());
        } catch (ApiServerException e) {
            System.out.printf("server error code = " + e.getStatusCode() + ", msg = " + e.getMessage());
        } catch (Exception e) {
            System.out.printf("other exception = " + e.getMessage());
        }

        return result != null ? result.getBody() : new GetUserDto();
    }
}
