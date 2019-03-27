package ru.langservice.translator.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslateResult implements TranslateResponse {
    String lang;
    String text;
    String translation;
}
