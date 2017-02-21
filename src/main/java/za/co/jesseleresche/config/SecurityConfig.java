package za.co.jesseleresche.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import za.co.jesseleresche.service.CustomAuthorizationCodeAccessTokenProvider;

import javax.servlet.Filter;
import java.util.Arrays;

/**
 * This contains the Spring Security beans and Configuration
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private OAuth2ClientContext oAuth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login", "/about").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class).exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login/deezer"))
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter deezerFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/deezer");
        OAuth2RestTemplate deezerTemplate = getOAuth2RestTemplate();
        deezerFilter.setRestTemplate(deezerTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(deezerResource().getUserInfoUri(), deezer().getClientId());
        tokenServices.setRestTemplate(deezerTemplate);
        deezerFilter.setTokenServices(tokenServices);
        return deezerFilter;
    }

    @Bean
    public OAuth2RestTemplate getOAuth2RestTemplate() {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(deezer(), oAuth2ClientContext);
        AccessTokenProvider customAccessTokenProvider = new AccessTokenProviderChain(Arrays.asList(new CustomAuthorizationCodeAccessTokenProvider()));
        oAuth2RestTemplate.setAccessTokenProvider(customAccessTokenProvider);
        return oAuth2RestTemplate;
    }

    @Bean
    @ConfigurationProperties("deezer.resource")
    public ResourceServerProperties deezerResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("deezer.client")
    public AuthorizationCodeResourceDetails deezer() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter oAuth2ClientContextFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(oAuth2ClientContextFilter);
        registration.setOrder(-100);
        return registration;
    }

    @Autowired
    public void setoAuth2ClientContext(OAuth2ClientContext oAuth2ClientContext) {
        this.oAuth2ClientContext = oAuth2ClientContext;
    }
}
