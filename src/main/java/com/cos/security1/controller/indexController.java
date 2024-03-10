package com.cos.security1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // veiw를 리턴하겠다!

public class indexController {
    @GetMapping({"","/"})
    public String index(){
        return "index";
    }
}
