package me.joshua.config;

import me.joshua.bangsong.BangsongConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  HandlerMapping,HandlerAdapter, ExceptionHandler customizing => WebMvcRegistration
 *  anything else => WebMvcConfigurer
 */
@Configuration
public class  WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/bs").allowedOrigins("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //overidding code here
    }

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new BangsongConverter());
//    }
}
