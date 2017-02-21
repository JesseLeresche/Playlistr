package za.co.jesseleresche.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import za.co.jesseleresche.model.User;
import za.co.jesseleresche.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static za.co.jesseleresche.helper.DataCreationHelper.createUser;

/**
 * This class tests the User Controller to ensure that it provides the required
 * responses to the various endpoints on available.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {

    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testGetUser() throws Exception {
        User testUser = createUser();

        given(userService.getUser()).willReturn(testUser);

        mockMvc.perform(get("/user/me").with(user("user.")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", testUser))
                .andExpect(view().name("user"));
    }

    @Autowired
    public void setContext(WebApplicationContext context) {
        this.context = context;
    }
}
