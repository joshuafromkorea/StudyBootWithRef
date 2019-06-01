package me.joshua.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  HandlerMapping,HandlerAdapter, ExceptionHandler customizing => WebMvcRegistration
 *  anything else => WebMvcConfigurer
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverters customCoverters(){
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
                return new HttpMessageConverters(converter);
    }

}
