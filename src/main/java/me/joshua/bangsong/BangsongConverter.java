package me.joshua.bangsong;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BangsongConverter implements Converter<String, Bangsong> {
    @Override
    public Bangsong convert(String s) {
        Bangsong bangsong = new Bangsong();
        bangsong.setId(Integer.valueOf(s));
        return bangsong;
    }
}
