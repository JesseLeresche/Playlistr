package za.co.jesseleresche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import za.co.jesseleresche.model.DeezerAuthentication;
import za.co.jesseleresche.model.User;

/**
 * Created by jesse on 2016/11/09.
 */
@Service
public class UserService {

    private RestOperations restOperations;

    @Value("${deezer.base.url}")
    private String deezerBaseUrl;

    public User getUser(String accessToken) {
        User user = restOperations.getForObject(deezerBaseUrl + "/user/me?access_token={accessToken}", User.class, accessToken);
        return user;
    }

    @Autowired
    public void setRestOperations(RestOperations restOperations) {
        this.restOperations = restOperations;
    }
}
