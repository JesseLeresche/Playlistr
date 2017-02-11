package za.co.jesseleresche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import za.co.jesseleresche.model.DeezerAuthentication;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * Service to allow application to Authorise with Deezer. The DeezerAuthntication token
 * is needed for certain requests to Deezer's API (such as creating a playlist etc.
 */
@Service
public class AuthenticationService {

    @Value("${deezer.token.url}")
    private String tokenUrl;

    @Value("${deezer.app.id}")
    private String appId;

    @Value("${deezer.app.secret}")
    private String appSecret;

    private RestOperations restOperations;

    public DeezerAuthentication createDeezerAuthentication(String code) {
        String authenticationString = restOperations.getForObject(tokenUrl + "?app_id={appId}&secret={secret}&code={code}", String.class, appId, appSecret, code);
        DeezerAuthentication deezerAuthentication = null;
        if (!StringUtils.isEmpty(authenticationString) && !"wrong code".equalsIgnoreCase(authenticationString)) {

            String accessToken = authenticationString.substring(authenticationString.indexOf("=") + 1, authenticationString.indexOf("&"));
            Long duration = Long.valueOf(authenticationString.substring(authenticationString.lastIndexOf("=") + 1));
            deezerAuthentication = new DeezerAuthentication(accessToken, duration);
        }
        return deezerAuthentication;
    }



    @Autowired
    public void setRestOperations(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

}
