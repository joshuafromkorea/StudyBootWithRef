package me.joshua.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {


    @Autowired
    HttpMessageConverters converters;

    @RequestMapping("/")
    public String index() {

        String concat = new String("");
        converters.getConverters().forEach(c-> System.out.println(c.getClass()));

        return "hello";
    }
}
