package egovframework.lab.controller;

import egovframework.lab.entity.UserSession;
import egovframework.lab.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserSessionController {
    private final UserSessionRepository userSessionRepository;

    @GetMapping("/user/{acctNum}/get.do")
    public String get(@PathVariable String acctNum) {
        System.out.println("+ acctNum = " + acctNum);
        Mono<UserSession> selected = userSessionRepository.selectOneUserSession(acctNum);
        UserSession userSession = selected.block();
        System.out.println(userSession.toString());
        System.out.println("isShowEventPopup = " + userSession.getLoginVO().getShowEventPopup());
        return "ok :)";
    }
}
