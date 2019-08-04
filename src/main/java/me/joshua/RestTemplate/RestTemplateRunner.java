package me.joshua.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class RestTemplateRunner implements ApplicationRunner {

    @Autowired
    RestTemplateBuilder builder;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//       RestTemplate template= builder.build();
//
//       GithubRepository[] result = template.getForObject("https://api.github.com/users/keesun/repos", GithubRepository[].class);
//        Arrays.stream(result).forEach(x->{
//            System.out.println(x.getId());
//            System.out.println(x.getName());
//            System.out.println(x.getUrl());
//        });


        WebClient client = webClientBuilder.baseUrl("https://api.github.com").build();

        Mono<GithubRepository[]> reposMono = client.get().uri("users/keesun/repos").retrieve().bodyToMono(GithubRepository[].class);
        Flux<GithubRepository> reposFlux = client.get().uri("users/keesun/repos").retrieve().bodyToFlux(GithubRepository.class);
        Flux<GithubRepository> reposFlux2 = client.get().uri("users/keesun/repos").retrieve().bodyToFlux(GithubRepository.class);

        reposMono.doOnSuccess(ra->{
            Arrays.stream(ra).forEach(r->{
                System.out.println("repos: " + r.getUrl());
            });
        }).subscribe();

        reposFlux.doOnNext(r->{
            System.out.println("name: " + r.getName());
        }).subscribe();

        reposFlux2.doOnNext(r->{
            System.out.println("id: " +r.getId());
        }).subscribe();

    }
}
