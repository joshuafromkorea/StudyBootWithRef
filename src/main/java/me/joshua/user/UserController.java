package me.joshua.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @Autowired
    HttpMessageConverters converters;

    @RequestMapping("/")
    public String index() {

        return "hello";
    }

    @GetMapping("/user")
    public User currentUser(){
        User user = new User();
        user.setAge(34);
        user.setName("joshua");

        return user;
    }


}
