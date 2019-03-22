package ru.langservice.translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.component.TranslatorUriService;
import ru.langservice.translator.domain.GetLangs;
import ru.langservice.translator.domain.TranslateResult;

import java.util.Map;

@RestController
public class TranslationController {

    @Autowired
    private TranslatorUriService translatorUriService;

    @RequestMapping("/langs")
    public Map<String, String> langs(RestTemplate restTemplate){
        GetLangs getLangs = restTemplate.getForObject(translatorUriService.getLangsUri(), GetLangs.class);
        return getLangs.getLangs();
    }

    @RequestMapping("/translate")
    public TranslateResult translate(RestTemplate restTemplate, @RequestParam(value="text") String text, @RequestParam(value="lang", defaultValue = "en-ru") String lang){
//       if (text == null || text.isEmpty()){
//           return null;
//       }

        LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("lang", lang);
        valueMap.add("text", text);
        valueMap.add("format", "plain");

        restTemplate.getForObject(translatorUriService.getTranslateUri(valueMap), GetLangs.class);

        TranslateResult translateResult = new TranslateResult();
        translateResult.setLang(lang);
        translateResult.setText(text);
        translateResult.setTranslation("");

        return translateResult;
    }

}
