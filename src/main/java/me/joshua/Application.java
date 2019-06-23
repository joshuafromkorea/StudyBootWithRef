package me.joshua;

import me.joshua.bangsong.BangsongConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;

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
        SpringApplication.run(Application.class, args);
    }

}
