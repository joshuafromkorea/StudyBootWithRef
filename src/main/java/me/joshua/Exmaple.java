package me.joshua;

import me.joshua.hello.HelloService;
import me.joshua.hello.JoshuaProperties;
import org.apache.catalina.loader.WebappClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class Exmaple {

    @Autowired
    HelloService helloService;

    @Autowired
    Environment environment;

    @Bean
    public ExitCodeGenerator exitCodeGenerator(){
        return () -> 42;
    }

    @RequestMapping("/")
    String home(){
        System.out.println(environment.getProperty("joshua.list", List.class));
        System.out.println(environment.getProperty("joshua.name"));
        return helloService.getMeassage();
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Exmaple.class);
        springApplication.addListeners(new MyListner());

        springApplication.run(args);

    }
}
