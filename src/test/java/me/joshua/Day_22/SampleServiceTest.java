package me.joshua.Day_22;

import me.joshua.Day_20.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(SampleService.class)
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Test
    public void foodTest(){
        mockRestServiceServer.expect(requestTo("/foo"))
                .andRespond(withSuccess("Kiwon", MediaType.TEXT_PLAIN));

        String name = sampleService.getName();
        assertThat(name).isEqualTo("Kiwon");
    }
}
