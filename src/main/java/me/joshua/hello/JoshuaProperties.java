package me.joshua.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("joshua")
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
