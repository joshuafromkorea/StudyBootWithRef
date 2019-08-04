package me.joshua.day_32;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

//@Component
public class MongoDataPopulator implements ApplicationRunner {

    @Autowired
    private MongoRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
