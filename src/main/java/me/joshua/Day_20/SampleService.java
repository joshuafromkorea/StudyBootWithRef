package me.joshua.Day_20;

import org.springframework.stereotype.Service;

@Service
public class SampleService {
    public String getName(){
        return "Remote Service";
    }

    public String getReady(){
        return "Yes I am ready";
    }
}
