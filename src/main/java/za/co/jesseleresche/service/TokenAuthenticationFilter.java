package za.co.jesseleresche.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import za.co.jesseleresche.model.DeezerAuthentication;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by jesse on 2017/02/12.
 */
public class TokenAuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initializing TokenAuthenticationFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() != null && securityContext.getAuthentication().isAuthenticated()){
            logger.info("User is already authenticated");
        } else {
            SecurityContextHolder.getContext().setAuthentication(new DeezerAuthentication("1234", 3600L));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("Shutting down TokenAuthenticationFilter");
    }
}
