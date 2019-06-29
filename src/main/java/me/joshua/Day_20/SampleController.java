package me.joshua.Day_20;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    SampleService service;

    @GetMapping("/foo")
    public String foo(){
        System.out.println(service.getReady());
        return service.getName();
    }
}
