package ru.langservice.translator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Translate {
    Integer code;
    String lang;
    List<String> text;
}