package me.joshua;

import me.joshua.Day_23.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class Application {

//    @Bean
//    public ConfigurableWebBindingInitializer initializer(){
//        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
//        ConfigurableConversionService conversionService = new FormattingConversionService();
//        conversionService.addConverter(new BangsongConverter());
//        initializer.setConversionService(conversionService);
//        return initializer;
//    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }


    @Bean
    public RouterFunction<ServerResponse> rount(){
        return route(GET("/book").and(accept(MediaType.APPLICATION_JSON)),
                req -> {
                    Book book1 = new Book();
                    book1.setTitle("Spring Webflux");
                    book1.setIsbn("1234");
                    Book book2 = new Book();
                    book2.setTitle("Spring Webflux is hard");
                    book2.setIsbn("1111");

                    Flux<Book> books = Flux.just(book1, book2);

                    return ok().contentType(MediaType.APPLICATION_JSON).body(books, Book.class);
                });

    }

}
