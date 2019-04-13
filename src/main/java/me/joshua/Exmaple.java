package me.joshua;

import me.joshua.hello.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Exmaple {

    @Autowired
    HelloService helloService;

    @RequestMapping("/")
    String home(){
        return helloService.getMeassage();
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Exmaple.class);
        springApplication.addListeners(new MyListner());
        springApplication.run(args);
    }
}
