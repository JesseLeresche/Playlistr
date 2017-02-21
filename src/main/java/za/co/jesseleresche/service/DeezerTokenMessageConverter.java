package za.co.jesseleresche.service;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jesse on 2017/02/13.
 */
public class DeezerTokenMessageConverter extends AbstractHttpMessageConverter implements GenericHttpMessageConverter {

    public DeezerTokenMessageConverter() {
        super(MediaType.TEXT_HTML);
    }

    @Override
    public boolean canRead(Type type, Class aClass, MediaType mediaType) {
        return MediaType.TEXT_HTML.getType().equals(mediaType.getType()) && MediaType.TEXT_HTML.getSubtype().equals(mediaType.getSubtype());
    }

    @Override
    public Object read(Type type, Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        DefaultOAuth2AccessToken oAuth2AccessToken = null;
        String authenticationString = new BufferedReader(new InputStreamReader(httpInputMessage.getBody()))
                .lines().collect(Collectors.joining("\n"));
        if (!StringUtils.isEmpty(authenticationString) && !"wrong code".equalsIgnoreCase(authenticationString)) {
            String accessToken = authenticationString.substring(authenticationString.indexOf("=") + 1, authenticationString.indexOf("&"));
            Long duration = Long.valueOf(authenticationString.substring(authenticationString.lastIndexOf("=") + 1));
            oAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
            LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(duration);
            Date expiration = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
            oAuth2AccessToken.setExpiration(expiration);
        }

        return oAuth2AccessToken;
    }

    @Override
    public boolean canWrite(Type type, Class aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public void write(Object o, Type type, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Arrays.asList(MediaType.TEXT_HTML);
    }

    @Override
    protected Object readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }

    @Override
    protected boolean supports(Class aClass) {
        return aClass.equals(OAuth2AccessToken.class);
    }
}
