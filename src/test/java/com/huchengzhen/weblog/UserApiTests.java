package com.huchengzhen.weblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived() {
        // TODO
    }
}
