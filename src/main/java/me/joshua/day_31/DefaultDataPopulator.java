package me.joshua.day_31;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class DefaultDataPopulator implements ApplicationRunner {

    @Autowired MeetingRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Meeting meeting = new Meeting();
        meeting.setTitle("new Meeting");
        meeting.setStartAt(new Date());
        repository.save(meeting);

        repository.findAll().forEach(m->{
            System.out.println("==============");
            System.out.println(m.getTitle()+" "+m.getStartAt());
        });


    }
}
