package ru.langservice.translator.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class TranslatorUriService {
    private static final Logger log = LoggerFactory.getLogger(TranslatorUriService.class);

    @Value("${translate.user.key}")
    private String uriUserKey;
    @Value("${translate.uri.scheme}")
    private String uriScheme;
    @Value("${translate.uri.host}")
    private String uriHost;
    @Value("${translate.uri.path}")
    private String uriPath;

    @Value("${translate.uri.path.getLangs}")
    private String langsPath;
    @Value("${translate.uri.path.translate}")
    private String translatePath;

    public URI getLangsUri(){
        return createServiceUri(langsPath);
    }

    public URI getTranslateUri(final MultiValueMap<String, String> queryParams){
        return createServiceUri(translatePath, queryParams);
    }

    private URI createServiceUri(final String path){
        return createServiceUri(path,
                );
    }

    private URI createServiceUri(final String path, final MultiValueMap<String, String> queryParams){
        queryParams.add("ui", "en");
        queryParams.add("key", uriUserKey);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(uriScheme)
                .host(uriHost)
                .path(uriPath + path)
                .queryParams(queryParams)
                .build();

        log.info("request: " + uriComponents.toUriString());
        return uriComponents.toUri();
    }
}
