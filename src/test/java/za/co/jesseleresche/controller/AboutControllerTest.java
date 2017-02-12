package za.co.jesseleresche.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * This class tests the About Controller to ensure that it provides the required
 * responses to the various endpoints on available.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AboutController.class)
public class AboutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAboutControllerReturnsAboutPage() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }
}
