package me.joshua.Day_20;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SampleService {

    RestTemplate restTemplate;

    public SampleService(RestTemplateBuilder builder){
        this.restTemplate = builder.build();
    }

    public String getName(){
        return restTemplate.getForObject("/foo", String.class);
    }

    public String getReady(){
        return "Yes I am ready";
    }
}
