package ru.langservice.translator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.dto.TranslateResult;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class TranslateServiceTest {

    @Autowired
    private TranslateService translationService;

    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.22.204.10", 3128));
        requestFactory.setProxy(proxy);
        return new RestTemplate(requestFactory);
    }

    @Test
    public void getLangTest() {
        Map<String, String> langs = this.translationService.getLangs(restTemplate());
        assertNotNull("langs is null!", langs);
        assertTrue("langs is empty!", !langs.isEmpty());
    }

    @Test
    public void getTranslationTest() {
        TranslateResult result = this.translationService.getTanslate(restTemplate(), "Hello, World!", "en-ru");
        assertNotNull("translate is null!", result);
        assertEquals("en-ru", result.getLang());
        assertEquals("Error code != 200", new Integer(200), result.getCode());
        assertEquals("Text size > 1", 1, result.getText().size());
        assertEquals("Text != Привет, Мир!", "Привет, Мир!", result.getText().get(0));
    }
}
