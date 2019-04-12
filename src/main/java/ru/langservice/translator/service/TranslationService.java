package ru.langservice.translator.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.langservice.translator.domain.Translation;
import ru.langservice.translator.repository.TranslationRepository;

@Service
@AllArgsConstructor
@Slf4j
public class TranslationService {

    private final TranslationRepository translationRepository;

    public Page<Translation> getTranslations(String filter, Long userId, Pageable pageable) {
        if (StringUtils.isEmpty(filter)) {
            log.debug("Filter is empty. Get all page");
            return translationRepository.findByUserId(userId, pageable);
        }
        log.debug("Filter: {}", filter);
        return translationRepository.findByLangAndUserId(filter, userId, pageable);
    }

    public Translation saveTranslation(Translation data){
        return translationRepository.save(data);
    }
}
