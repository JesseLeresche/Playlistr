package za.co.jesseleresche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import za.co.jesseleresche.model.User;

import java.security.Principal;

/**
 * Created by jesse on 2016/11/09.
 */
@Service
public class UserService {

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Value("${deezer.base.url}")
    private String deezerBaseUrl;

    public User getUser(){
        return oAuth2RestTemplate.getForObject(deezerBaseUrl + "/user/me", User.class);
    }

    @Autowired
    public void setoAuth2RestTemplate(OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }
}
