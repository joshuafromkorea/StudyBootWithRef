package me.joshua.bangsong;

import org.springframework.hateoas.ResourceSupport;

public class BangsongResource extends ResourceSupport {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
