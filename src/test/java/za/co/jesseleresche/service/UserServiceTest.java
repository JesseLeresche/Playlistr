package za.co.jesseleresche.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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

    private RestOperations restOperations;

    @Before
    public void setup(){
        restOperations = Mockito.mock(RestOperations.class);

        userService = new UserService();
        userService.setRestOperations(restOperations);
    }

    @Test
    public void testGetUserDetailsSuccess(){
        User mockUser = createUser();

        when(restOperations.getForObject(anyString(), eq(User.class), anyString())).thenReturn(mockUser);

        User returnedUser = userService.getUser("1");

        assertNotNull(returnedUser);
        assertEquals(mockUser.getId(), returnedUser.getId());
        assertEquals(mockUser.getName(), returnedUser.getName());
    }
}
