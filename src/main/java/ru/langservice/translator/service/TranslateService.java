package ru.langservice.translator.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.dto.GetLangs;
import ru.langservice.translator.dto.TranslateResult;
import ru.langservice.translator.exception.NoLangsAvailableException;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class TranslateService {
    private final TranslateUriService translateUriService;

    @Cacheable(value = "langs", key = "#root.methodName")
    public Map<String, String> getLangs(RestTemplate restTemplate) {
        GetLangs getLangs;
        try{
            getLangs = restTemplate.getForObject(translateUriService.getLangsUri(), GetLangs.class);
        } catch (Exception e){
            log.error("No langs. Caught exception: {}. Exception message: {}", e.getClass().getCanonicalName(), e.getMessage());
            log.warn("Use default languages");
            return getDefaultLangs();
        }

        if (getLangs == null || getLangs.getLangs() == null || getLangs.getLangs().isEmpty()) {
            log.debug("No langs available");
            throw new NoLangsAvailableException();
        }

        log.trace("Langs: {}", getLangs.getLangs());
        return getLangs.getLangs();
    }

    public TranslateResult getTranslate(RestTemplate restTemplate, String text, String lang) {
        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("lang", lang);
        valueMap.add("text", text);
        valueMap.add("format", "plain");

        log.trace("GetTranslate valueMap: {}", valueMap);

        TranslateResult translateResult;
        try{
            translateResult = restTemplate.getForObject(translateUriService.getTranslateUri(valueMap), TranslateResult.class);
        } catch (Exception e ){
            log.error("No translation data. Caught exception: {}. Exception message: {}", e.getClass().getCanonicalName(), e.getMessage());
            return new TranslateResult();
        }
        return translateResult;
    }

    public Map<String, String> getDefaultLangs(){
        HashMap<String, String> langs = new HashMap<>(2);
        langs.put("en", "English");
        langs.put("ru", "Russian");
        return langs;
    }
}
