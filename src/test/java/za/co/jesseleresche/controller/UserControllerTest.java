package za.co.jesseleresche.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.jesseleresche.model.User;
import za.co.jesseleresche.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static za.co.jesseleresche.helper.DataCreationHelper.createDeezerAuthentication;
import static za.co.jesseleresche.helper.DataCreationHelper.createMockHttpSession;
import static za.co.jesseleresche.helper.DataCreationHelper.createUser;

/**
 * This class tests the User Controller to ensure that it provides the required
 * responses to the various endpoints on available.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUser() throws Exception {
        User user = createUser();

        given(userService.getUser(createDeezerAuthentication().getAccessToken())).willReturn(user);

        mockMvc.perform(get("/user").session(createMockHttpSession()).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("user"));
    }
}
