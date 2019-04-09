package ru.langservice.translator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.langservice.translator.domain.Translation;

public interface TranslationRepository extends CrudRepository<Translation, Long> {
    Page<Translation> findByLang(String lang, Pageable pageable);
    Page<Translation> findByUserId(Long userId, Pageable pageable);
    Page<Translation> findByLangAndUserId(String lang, Long userId, Pageable pageable);
}
