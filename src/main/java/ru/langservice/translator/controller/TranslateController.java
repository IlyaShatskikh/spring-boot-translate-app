package ru.langservice.translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.component.TranslateUriService;
import ru.langservice.translator.domain.GetLangs;
import ru.langservice.translator.domain.Translate;
import ru.langservice.translator.domain.response.Error;
import ru.langservice.translator.domain.response.TranslateResponse;
import ru.langservice.translator.domain.response.TranslateResult;

import java.util.Map;

@RestController
public class TranslateController {

    @Autowired
    private TranslateUriService translatorUriService;

    @RequestMapping("/langs")
    public Map<String, String> langs(RestTemplate restTemplate) {
        GetLangs getLangs = restTemplate.getForObject(translatorUriService.getLangsUri(), GetLangs.class);
        return getLangs.getLangs();
    }

    @RequestMapping("/translate")
    public TranslateResponse translate(RestTemplate restTemplate, @RequestParam(value = "text") String text, @RequestParam(value = "lang", defaultValue = "en-ru") String lang) {
        if (text == null || text.isEmpty()) {
            return new Error(HttpStatus.BAD_REQUEST.value(), "No text found");
        }

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("lang", lang);
        valueMap.add("text", text);
        valueMap.add("format", "plain");

        Translate translate;
        try {
            translate = restTemplate.getForObject(translatorUriService.getTranslateUri(valueMap), Translate.class);
        } catch (HttpStatusCodeException ex) {
            return new Error(ex);
        }

        TranslateResult translateResult = new TranslateResult();
        translateResult.setLang(lang);
        translateResult.setText(text);
        if (translate != null) translateResult.setTranslation(String.join(" ", translate.getText()));

        return translateResult;
    }
}
