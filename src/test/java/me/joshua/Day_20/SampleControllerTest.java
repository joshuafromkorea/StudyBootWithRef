package me.joshua.Day_20;

import me.joshua.Day_20.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleControllerTest {

    @Autowired
    MockMvc mockMVc;

    @Autowired
    WebTestClient webTestClient;


    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    SampleService sampleService;
    @SpyBean
    SampleService spySampleService;

    @Test
    public void testSpyBean(){
        given(sampleService.getName()).willReturn("this is mock");
        System.out.println(sampleService.getName());
        System.out.println(sampleService.getReady());

        given(spySampleService.getName()).willReturn("this is spy");
        System.out.println(spySampleService.getName());
        System.out.println(spySampleService.getReady());
    }

    @Test
    public void testFooWithTestRestTemplate() {
        String body = this.restTemplate.getForObject("/foo", String.class);
        assertThat(body).isEqualTo("Hello");
    }

    @Test
    public void testFooWithWebTestClient(){
        given(sampleService.getName()).willReturn("mock");
        webTestClient.get().uri("/foo").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("mock");
    }

    @Test
    public void testFoo() throws Exception {
        assertThat(mockMVc).isNotNull();
        mockMVc.perform(get("/foo")).andExpect(status().isOk())
                .andDo(print());
    }

}
