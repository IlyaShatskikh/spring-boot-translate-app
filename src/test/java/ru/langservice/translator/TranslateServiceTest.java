package ru.langservice.translator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.langservice.translator.service.TranslateService;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class TranslateServiceTest {

    @Autowired
    private TranslateService translationService;

    public RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.22.204.10", 3128));
        requestFactory.setProxy(proxy);
        return new RestTemplate(requestFactory);
    }

    @Test
    public void getLangTest(){
        Map<String, String> langs = this.translationService.getLangs(restTemplate());
//        langs.entrySet()
    }
}
