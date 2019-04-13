package me.joshua.hello;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getMeassage(){
        return "Hello Spring Boot 2.0";
    }
}
