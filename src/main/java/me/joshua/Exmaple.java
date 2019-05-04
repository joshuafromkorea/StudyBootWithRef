package me.joshua;

import me.joshua.hello.HelloService;
import me.joshua.hello.JoshuaProperties;
import me.joshua.hello.MyPojo;
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
@EnableConfigurationProperties(JoshuaProperties.class)
public class Exmaple {

    @Autowired
    HelloService helloService;

    @Autowired
    Environment environment;

    @Autowired
    JoshuaProperties joshuaProperties;

    @Bean
    public ExitCodeGenerator exitCodeGenerator(){
        return () -> 42;
    }

    @RequestMapping("/")
    String home(){
        System.out.println(environment.getProperty("joshua.name"));
        System.out.println(environment.getProperty("joshua.myPojo[0].name"));
        System.out.println(environment.getProperty("joshua.myPojo[0].desc"));
        System.out.println(environment.getProperty("joshua.myPojo[1].name"));
        System.out.println(environment.getProperty("joshua.myPojo[1].desc"));
        System.out.println("================================================");
        System.out.println(joshuaProperties.getName());
        System.out.println(joshuaProperties.getMyPojo().get(0).getName());
        System.out.println(joshuaProperties.getMyPojo().get(0).getDesc());
        System.out.println(joshuaProperties.getMyPojo().get(1).getName());
        System.out.println(joshuaProperties.getMyPojo().get(1).getDesc());
        return helloService.getMeassage();
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Exmaple.class);
        springApplication.addListeners(new MyListner());

        springApplication.run(args);

    }
}
