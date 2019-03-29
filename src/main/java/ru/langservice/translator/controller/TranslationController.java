package ru.langservice.translator.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.langservice.translator.domain.Translation;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.repository.TranslationRepository;

import java.util.Map;

@Controller
@AllArgsConstructor
public class TranslationController {
    private final TranslationRepository translationRepository;

    @GetMapping("/")
    public String welcome(Map<String, Object> model){
        return "welcome";
    }

    @GetMapping("/translation")
    public String translation(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Translation> translations;
        translations = (filter == null || filter.isEmpty()) ? translationRepository.findAll() : translationRepository.findByLang(filter);
        model.addAttribute("translations", translations);
        model.addAttribute("filter", filter);
        return "translation";
    }

    @PostMapping("post")
    public String add(@AuthenticationPrincipal User user, @RequestParam String text, @RequestParam String lang, Map<String, Object> model) {
        Translation translation = new Translation(text, lang, user);
        translationRepository.save(translation);

        //todo fix it!
        Iterable<Translation> translations = translationRepository.findAll();
        model.put("translations", translations);
        return "translation";
    }
}
