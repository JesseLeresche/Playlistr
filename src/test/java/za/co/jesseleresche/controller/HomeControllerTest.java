package za.co.jesseleresche.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.jesseleresche.model.DeezerAuthentication;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static za.co.jesseleresche.helper.DataCreationHelper.createMockHttpSession;


/**
 * This class tests the Home Controller to ensure that it provides the required
 * responses to the various endpoints on available.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserLoggedOutOnHomePage() throws Exception {
        MockHttpSession mockHttpSession = createMockHttpSession();
        mockHttpSession.setAttribute("deezerAuthentication", new DeezerAuthentication("1234", -3600L));
        mockMvc.perform(get("/").session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isUserLoggedIn", false))
                .andExpect(content().string(containsString("Login to Deezer")))
                .andExpect(view().name("home"));
    }

    @Test
    public void testUserLoggedInOnHomePage() throws Exception {
        mockMvc.perform(get("/").session(createMockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isUserLoggedIn", true))
                .andExpect(content().string(not(containsString("Login to Deezer"))))
                .andExpect(view().name("home"));
    }
}
