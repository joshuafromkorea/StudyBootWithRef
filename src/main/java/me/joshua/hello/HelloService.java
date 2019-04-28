package me.joshua.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelloService {

    //name으로 정의된 프로퍼티 값을 가져오는 어노테이션
    @Value("${name}")
    String name;

    public String getMeassage(){return "Hello "+name; }

//    @PostConstruct
    public void init(){
        throw new RuntimeException("Intended Exception");
    }
}
