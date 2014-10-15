package pl.java.scalatech.test.contoller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.java.scalatech.config.MongoDBConfig;
import pl.java.scalatech.config.MongoRepositoryConfig;
import pl.java.scalatech.config.PropConfig;
import pl.java.scalatech.config.RestConfig;
import pl.java.scalatech.config.ServiceConfig;
import pl.java.scalatech.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoDBConfig.class, MongoRepositoryConfig.class, PropConfig.class, WebConfig.class, RestConfig.class, ServiceConfig.class })
@WebAppConfiguration
@Slf4j
public class FileControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Mockito.reset(fileServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldHealthControllerWorks() throws Exception {
        mockMvc.perform(get("/api/appContext")).andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
    }

}