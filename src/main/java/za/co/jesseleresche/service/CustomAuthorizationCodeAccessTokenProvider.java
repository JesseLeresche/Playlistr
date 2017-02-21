package za.co.jesseleresche.service;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by jesse on 2/15/17.
 */
public class CustomAuthorizationCodeAccessTokenProvider extends AuthorizationCodeAccessTokenProvider {

    @Override
    protected ResponseExtractor<OAuth2AccessToken> getResponseExtractor() {
        return new HttpMessageConverterExtractor<>(OAuth2AccessToken.class, getHttpMessageConverters());
    }

    private List<HttpMessageConverter<?>> getHttpMessageConverters() {
        List<HttpMessageConverter<?>> httpMessageConverters = new RestTemplate().getMessageConverters();
        httpMessageConverters.add(new DeezerTokenMessageConverter());
        return httpMessageConverters;
    }

    @Override
    protected ResponseExtractor<ResponseEntity<Void>> getAuthorizationResponseExtractor() {
        return new HttpMessageConverterExtractor<ResponseEntity<Void>>(OAuth2RestTemplate.class, getHttpMessageConverters());
    }
}
