package me.joshua.Day_21;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@JsonTest
public class SampleJsonTest {

    JacksonTester<Sample> sampleJacksonTester;

    @Before
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void testJson() throws IOException {
        Sample sample = new Sample();
        sample.setName("kiwon");
        sample.setNumber(100);

        assertThat(this.sampleJacksonTester.write(sample)).hasJsonPathStringValue("@.name")
                .extractingJsonPathStringValue("@.name").isEqualTo("kiwon");
        assertThat(this.sampleJacksonTester.write(sample)).hasJsonPathNumberValue("@.number")
                .extractingJsonPathNumberValue("@.number").isEqualTo(100);
    }
}
