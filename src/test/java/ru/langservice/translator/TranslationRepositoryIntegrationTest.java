package ru.langservice.translator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.langservice.translator.domain.Translation;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.repository.TranslationRepository;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = "spring.jpa.properties.hibernate.default_schema=")
public class TranslationRepositoryIntegrationTest {

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void add_thenReturnAll(){
        Translation translation = new Translation("test string", "en-ru", new User());
        entityManager.persist(translation);
        entityManager.flush();

        Iterable<Translation> all = translationRepository.findAll();

        Assert.assertTrue(all.iterator().hasNext());
        assertThat(translation.getText())
                .isEqualTo(all.iterator().next().getText());
    }
}
