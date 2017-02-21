package za.co.jesseleresche.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * This class tests the Home Controller to ensure that it provides the required
 * responses to the various endpoints on available.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HomeControllerTest {

    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testUserLoggedOutOnHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Login to Deezer")))
                .andExpect(view().name("home"));
    }

    @Test
    public void testUserLoggedInOnHomePage() throws Exception {
        mockMvc.perform(get("/").with(user("user")))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("Login to Deezer"))))
                .andExpect(view().name("home"));
    }

    @Autowired
    public void setContext(WebApplicationContext context) {
        this.context = context;
    }
}
