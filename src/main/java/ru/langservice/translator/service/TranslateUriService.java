package ru.langservice.translator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class TranslateUriService {
    @Value("${translate.user.key}")
    private String uriUserKey;

    @Value("${translate.uri}")
    private String uri;

    @Value("${translate.uri.path.getLangs}")
    private String langsPath;
    @Value("${translate.uri.path.translate}")
    private String translatePath;

    public URI getLangsUri() {
        return createServiceUri(langsPath);
    }

    public URI getTranslateUri(final MultiValueMap<String, String> queryParams) {
        return createServiceUri(translatePath, queryParams);
    }

    private URI createServiceUri(final String path) {
        return createServiceUri(path, new LinkedMultiValueMap<>());
    }

    private URI createServiceUri(final String path, final MultiValueMap<String, String> queryParams) {
        queryParams.add("ui", "en");
        queryParams.add("key", uriUserKey);

        if (uriUserKey == null){
            log.error("user key is empty!");
        }
        log.trace("Query parameters: {}", queryParams);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(uri + path).queryParams(queryParams).build();

        log.debug("request: " + uriComponents.toUriString());
        return uriComponents.toUri();
    }
}
