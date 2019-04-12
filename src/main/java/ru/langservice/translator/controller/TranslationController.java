package ru.langservice.translator.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.dto.PageWrapper;
import ru.langservice.translator.domain.Translation;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.dto.TranslateResult;
import ru.langservice.translator.exception.NoLangsAvailableException;
import ru.langservice.translator.service.TranslateService;
import ru.langservice.translator.service.TranslationService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class TranslationController {
    private final TranslateService translateService;
    private final TranslationService translationService;
    private final RestTemplate restTemplate;

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        return "welcome";
    }

    @GetMapping("/translation")
    public String translation(@AuthenticationPrincipal User user, @RequestParam(required = false, defaultValue = "") String filter, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Translation> page = translationService.getTranslations(filter, user.getId(), pageable);

        try{
            Map<String, String> langs = translateService.getLangs(restTemplate);
            model.addAttribute("langs", langs);
        } catch (NoLangsAvailableException | HttpClientErrorException ex){
            log.error("No langs. Exception message: {}", ex.getMessage());
            model.addAttribute("langs", Collections.EMPTY_MAP);
        }

        model.addAttribute("page", new PageWrapper<>(page));
        model.addAttribute("url", "/translation");
        model.addAttribute("filter", filter);
        return "translation";
    }

    @PostMapping("post")
    public String add(@AuthenticationPrincipal User user, @Valid Translation translation, BindingResult bindingResult, Model model) {
        translation.setUser(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            log.debug("Errors: {}", errors);
            model.addAllAttributes(errors);
            model.addAttribute("translation", translation);

            return "translation";
        }

        log.trace("Getting translation...");

        TranslateResult translateResult = translateService.getTranslate(restTemplate, translation.getOrigText(), translation.getLang());
        if (translateResult != null && translateResult.getText() != null && !translateResult.getText().isEmpty()) {
            log.debug("Translation received successfully");
            translation.setResultText(String.join(" ", translateResult.getText()));
        } else {
            log.warn("No translation received");
        }

        log.debug("Save translation: {}", translation);
        model.addAttribute("translation", null);

        translationService.saveTranslation(translation);

        return "redirect:/translation";
    }
}
