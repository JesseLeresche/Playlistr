package za.co.jesseleresche.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by jesse on 2016/11/07.
 */
public class DeezerAuthentication implements Authentication{

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isValidToken();
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
