package me.joshua.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThymeleafController {

    @GetMapping("${index.th.path}")
    public String hello(Model model, @RequestParam String name){
        model.addAttribute("name",name);
        return "helloThymeleaf";
    }
}

