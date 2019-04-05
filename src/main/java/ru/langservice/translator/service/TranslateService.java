package ru.langservice.translator.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.dto.GetLangs;
import ru.langservice.translator.exception.NoLangsAvailableException;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class TranslateService {
    private final TranslateUriService translateUriService;

    @Cacheable(value = "langs", key = "#root.methodName" )
    public Map<String, String> getLangs(RestTemplate restTemplate){
        GetLangs getLangs = restTemplate.getForObject(translateUriService.getLangsUri(), GetLangs.class);
        if (getLangs == null || getLangs.getLangs() == null || getLangs.getLangs().isEmpty()){
            log.debug("No langs available");
            throw new NoLangsAvailableException();
        }
        log.trace("Langs: {}", getLangs.getLangs());
        return getLangs.getLangs();
    }
}
