package me.joshua.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties
@Validated
public class JoshuaProperties {

    private String name;

    private List<MyPojo> myPojo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MyPojo> getMyPojo() {
        return myPojo;
    }

    public void setMyPojo(List<MyPojo> myPojo) {
        this.myPojo = myPojo;
    }
}
