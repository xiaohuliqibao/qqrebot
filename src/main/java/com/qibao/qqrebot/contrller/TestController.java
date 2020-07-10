package com.qibao.qqrebot.contrller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String MyFunction() {
        return "qibao";
    }
}