package me.joshua.freemarker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FreemarkerController {

    @GetMapping("/hello/freemarker")
    public String hello(Model model, @RequestParam String name){
        model.addAttribute("name",name);
        return "hello";
    }

}
