package za.co.jesseleresche.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestOperations;
import za.co.jesseleresche.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static za.co.jesseleresche.helper.DataCreationHelper.createUser;

/**
 * Created by jesse on 2016/11/10.
 */
public class UserServiceTest {

    private UserService userService;

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Before
    public void setup(){
        oAuth2RestTemplate = Mockito.mock(OAuth2RestTemplate.class);

        userService = new UserService();
        userService.setoAuth2RestTemplate(oAuth2RestTemplate);
    }

    @Test
    public void testGetUserDetailsSuccess(){
        User mockUser = createUser();

        when(oAuth2RestTemplate.getForObject(anyString(), eq(User.class))).thenReturn(mockUser);

        User returnedUser = userService.getUser();

        assertNotNull(returnedUser);
        assertEquals(mockUser.getId(), returnedUser.getId());
        assertEquals(mockUser.getName(), returnedUser.getName());
    }
}
