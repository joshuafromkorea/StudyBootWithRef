package me.joshua.Day_23;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookController {

    @GetMapping("/foo_23")
    public String getFoo(){
        return "kiwon";
    }

    @GetMapping("/flux")
    public Flux<String> flux(){
        return Flux.just("a", "b","c");
    }

    @GetMapping("/mono")
    public Mono<String> mono(){
        return Mono.just("mono");
    }
}
