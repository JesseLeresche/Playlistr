package za.co.jesseleresche.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestOperations;
import za.co.jesseleresche.model.DeezerAuthentication;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by jesse on 2016/11/09.
 */
public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private RestOperations restOperationsMock;

    @Before
    public void setUp() {
        restOperationsMock = Mockito.mock(RestOperations.class);

        authenticationService = new AuthenticationService();
        authenticationService.setRestOperations(restOperationsMock);
    }

    @Test
    public void testAccessTokenGeneratedSuccessfully() {
        String accessToken = "frlAqvHMIKs0E7vV4KNXILnGkYtvLk01ZGuZ8gpv9fN1jrt0KS4";
        Long duration = 463L;
        String accessTokenResponse = "access_token=" + accessToken + "&expires=" + duration.toString();
        when(restOperationsMock.getForObject(anyObject(), eq(String.class), anyLong(), anyString(), anyString())).thenReturn(accessTokenResponse);

        DeezerAuthentication deezerAuthentication = authenticationService.createDeezerAuthentication("");

        assertNotNull(deezerAuthentication);
        assertEquals(accessToken, deezerAuthentication.getAccessToken());
        assertTrue(deezerAuthentication.getExpiryTime().isAfter(LocalDateTime.now()));
    }

    @Test
    public void testAccessTokenNotGeneratedSuccessfullyBecauseWrongCodeSupplied() {
        when(restOperationsMock.getForObject(anyObject(), eq(String.class))).thenReturn("wrong code");

        DeezerAuthentication authentication = authenticationService.createDeezerAuthentication("");

        assertNull("The DeezerAuthentication object should be null when a token cannot be generated", authentication);
    }

    @Test
    public void testAccessTokenNotGeneratedSuccessfullyBecauseNullOrEmptyResponse() {
        when(restOperationsMock.getForObject(anyObject(), eq(String.class))).thenReturn("");

        DeezerAuthentication authentication = authenticationService.createDeezerAuthentication("");

        assertNull("The DeezerAuthentication object should be null when a token cannot be generated", authentication);
    }

}
