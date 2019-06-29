package me.joshua;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/foo")
    public String foo(){
        return "Hello";
    }
}
