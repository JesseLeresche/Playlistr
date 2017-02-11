package za.co.jesseleresche.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.time.LocalDateTime;

/**
 * Created by jesse on 2016/11/07.
 */
public class DeezerAuthentication {

    private static final String NULL_STRING = "null";
    private String accessToken;
    private LocalDateTime expiryTime;

    public DeezerAuthentication(String accessToken, Long expires) {
        this.accessToken = accessToken;
        this.expiryTime = LocalDateTime.now().plusSeconds(expires);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public Boolean isValidToken(){
        return validateAccessToken() && validateTokenExpiryDate();
    }

    private Boolean validateAccessToken() {
        Boolean validToken = true;
        if (NULL_STRING.equalsIgnoreCase(accessToken)){
            validToken = false;
        }
        return validToken;
    }

    private Boolean validateTokenExpiryDate() {
        Boolean validToken = true;
        if ((expiryTime == null) ||  LocalDateTime.now().isAfter(expiryTime)){
            validToken = false;
        }
        return validToken;
    }
}
