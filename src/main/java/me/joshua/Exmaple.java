package me.joshua;

import me.joshua.hello.HelloService;
import org.apache.catalina.loader.WebappClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Exmaple {

    @Autowired
    HelloService helloService;

    @Bean
    public ExitCodeGenerator exitCodeGenerator(){
        return () -> 42;
    }

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
