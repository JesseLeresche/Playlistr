package za.co.jesseleresche.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import za.co.jesseleresche.service.TokenAuthenticationFilter;

/**
 * Created by jesse on 2017/02/12.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/static/**", "/layout", "/home", "/about").permitAll()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(), AnonymousAuthenticationFilter.class);
        super.configure(http);
    }
}
