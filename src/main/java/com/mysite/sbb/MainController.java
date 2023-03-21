package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링부트의 컨트롤러
public class MainController {

    @GetMapping("/sbb") // 요청된 URL과의 매핑을 담당
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/question/list"; //완전히 새로운  URL로 요청
    }
}