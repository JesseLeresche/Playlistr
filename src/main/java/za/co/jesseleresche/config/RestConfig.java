package za.co.jesseleresche.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import za.co.jesseleresche.service.AuthenticationService;
import za.co.jesseleresche.service.PlaylistService;
import za.co.jesseleresche.service.UserService;


/**
 * Created by jesse on 2016/11/07.
 */
@Configuration
public class RestConfig {

    @Bean
    public RestOperations createRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public ClientHttpRequestFactory createClientHttpRequestFactory(@Value("${connect.timeout}") Integer connectTimeout, @Value("${read.timeout}") Integer readTimeout) {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setReadTimeout(readTimeout);
        httpComponentsClientHttpRequestFactory.setConnectTimeout(connectTimeout);
        return httpComponentsClientHttpRequestFactory;
    }

    @Bean
    public StringHttpMessageConverter createStringHttpMessageConverter() {
        return new StringHttpMessageConverter();
    }

    @Bean
    public HttpMessageConverters createHttpMessageConverters(StringHttpMessageConverter stringHttpMessageConverter) {
        return new HttpMessageConverters(stringHttpMessageConverter);
    }

    @Bean
    public UserService createUserService() {
        return new UserService();
    }

    @Bean
    public AuthenticationService createAuthenticationService() {
        return new AuthenticationService();
    }

    @Bean
    public PlaylistService createPlaylistService() {
        return new PlaylistService();
    }
}
