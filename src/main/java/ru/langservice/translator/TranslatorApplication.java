package ru.langservice.translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.langservice.translator.domain.GetLangs;

import java.net.URI;

@SpringBootApplication
public class TranslatorApplication {

	private static final Logger log = LoggerFactory.getLogger(TranslatorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TranslatorApplication.class, args);
	}

}
