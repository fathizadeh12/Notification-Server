package ca.iconish.notification.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "It works";
    }

}

