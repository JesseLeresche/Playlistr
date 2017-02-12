package za.co.jesseleresche.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.co.jesseleresche.model.DeezerAuthentication;
import za.co.jesseleresche.service.AuthenticationService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests the Authentication Controller to ensure that it provides the required
 * responses to the various endpoints on available.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@TestPropertySource("classpath:application.properties")
public class AuthenticationControllerTests {

    private static final String AUTH_CODE = "1234";
    private static final String AUTH_ERROR = "user_denied";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Value("${deezer.auth.url}")
    private String authUrl;

    @Test
    public void getCode() throws Exception {
        mockMvc.perform(get("/authenticate").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(authUrl));
    }

    @Test
    public void testReceiveAuthenticationCodeAndSuccessfullyGenerateToken() throws Exception {
        DeezerAuthentication deezerAuthentication = new DeezerAuthentication("5678", 3600L);
        given(authenticationService.createDeezerAuthentication(AUTH_CODE))
                .willReturn(deezerAuthentication);
        mockMvc.perform(get("/authenticate/receiveCode?code=" + AUTH_CODE).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/playlists"))
                .andExpect(request().sessionAttribute("deezerAuthentication", deezerAuthentication));
    }

    @Test
    public void testReceiveAuthenticationCodeAndCannotGenerateToken() throws Exception {
        given((authenticationService.createDeezerAuthentication(AUTH_CODE)))
                .willReturn(null);
        String errorReason = "Could not generate Token";
        mockMvc.perform(get("/authenticate/receiveCode?code=" + AUTH_CODE).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorReason", errorReason))
                .andExpect(model().attribute("success", false))
                .andExpect(view().name("authenticate"))
                .andExpect(content().string(containsString(errorReason)));
    }

    @Test
    public void testReceiveAuthenticationError() throws Exception {
        mockMvc.perform(get("/authenticate/receiveCode?error_reason=" + AUTH_ERROR).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorReason", AUTH_ERROR))
                .andExpect(model().attribute("success", false))
                .andExpect(view().name("authenticate"))
                .andExpect(content().string(containsString(AUTH_ERROR)));
    }
}
