package ru.langservice.translator.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.domain.Translation;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.repository.TranslationRepository;
import ru.langservice.translator.service.TranslateService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class TranslationController {
    private final TranslationRepository translationRepository;
    private final TranslateService translateService;
    private final RestTemplate restTemplate;

    @GetMapping("/")
    public String welcome(Map<String, Object> model){
        return "welcome";
    }

    @GetMapping("/translation")
    public String translation(@AuthenticationPrincipal User user, @RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Translation> translations;
        if (StringUtils.isEmpty(filter)){
            log.debug("Filter is empty. Get all translations");
            translations = translationRepository.findByUserId(user.getId());
        }
        else {
            log.debug("Filter: {}", filter);
            translations = translationRepository.findByLangAndUserId(filter, user.getId());
        }
        log.debug("Get langs");
        Map<String, String> langs = translateService.getLangs(restTemplate);
        model.addAttribute("langs", langs);
        model.addAttribute("translations", translations);
        model.addAttribute("filter", filter);
        return "translation";
    }

    @PostMapping("post")
    public String add(@AuthenticationPrincipal User user, @Valid Translation translation, BindingResult bindingResult, Model model) {
        translation.setUser(user);
        if (bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            log.debug("Errors: {}", errors);
            model.addAllAttributes(errors);
            model.addAttribute("translation", translation);
        } else {
            log.debug("Save translation: {}", translation);
            model.addAttribute("translation", null);
            translationRepository.save(translation);
        }

        log.debug("Get langs");
        Map<String, String> langs = translateService.getLangs(restTemplate);
        model.addAttribute("langs", langs);

        Iterable<Translation> translations = translationRepository.findAll();
        model.addAttribute("translations", translations);
        return "translation";
    }
}
