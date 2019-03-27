package ru.langservice.translator.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.client.HttpStatusCodeException;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error implements TranslateResponse {
    Integer code;
    String text;

    public Error(HttpStatusCodeException ex){
        this.setCode(ex.getStatusCode().value());
        this.setText(ex.getLocalizedMessage());
    }
}


