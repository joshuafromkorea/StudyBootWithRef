package me.joshua.day_32;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MeetingMongoRepository extends MongoRepository<Meeting, String> {

    List<Meeting> findByAddress(String address);
}
