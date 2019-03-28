package ru.langservice.translator.repository;

import org.springframework.data.repository.CrudRepository;
import ru.langservice.translator.domain.Translation;

import java.util.List;

public interface TranslationRepository extends CrudRepository<Translation, Long> {
    List<Translation> findByLang(String lang);
}
