package me.joshua.bangsong;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class BangsongController {

    @GetMapping("/bs")
    public Bangsong bangsong (){
        Bangsong bangsong = new Bangsong();
        bangsong.setStreamer("kiwon");
        bangsong.setTitle("레스링부");
        return bangsong;
    }

    @GetMapping("/bs/{id}")
    public BangsongResource getBangsong(@PathVariable("id") Bangsong bangsong){
        if(bangsong.getId()==100){
            throw new BangsongException();
        }

        BangsongResource resource = new BangsongResource();
        resource.setTitle(bangsong.getId()+ " 번째 방송 중입니다.");

        Link link = linkTo(BangsongController.class).slash("bs").slash(bangsong.getId()).withSelfRel();
        Link listLink = linkTo(BangsongController.class).slash("bs").withRel("bangsongList");

        resource.add(link, listLink);

        return resource;
    }

    @ExceptionHandler(BangsongException.class)
    public ResponseEntity<String> handlerException(BangsongException e){
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
}
