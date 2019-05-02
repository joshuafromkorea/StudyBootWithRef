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

    @Autowired
    JoshuaProperties joshuaProperties;

    public String getMeassage(){return "Hello "+joshuaProperties.getName()+ " "
            + joshuaProperties.getMyPojo().size(); }

//    @PostConstruct
    public void init(){
        throw new RuntimeException("Intended Exception");
    }
}
