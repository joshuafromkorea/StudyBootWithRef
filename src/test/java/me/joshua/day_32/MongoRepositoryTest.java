package me.joshua.day_32;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MongoRepositoryTest {

    @Autowired
    MeetingMongoRepository meetingMongoRepository;

    @Test
    public void findByAddress(){
        Meeting meeting = new Meeting();
        meeting.setTitle("mongo study");
        meeting.setAddress("suji");

        meetingMongoRepository.save(meeting);


        List<Meeting> meetingList=  meetingMongoRepository.findByAddress("suji");

        assertThat(meetingList.size()).isEqualTo(1);
        assertThat(meetingList).contains(meeting);
    }
}
