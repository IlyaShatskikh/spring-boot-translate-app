package ru.langservice.translator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetLangs {
    List<String> dirs;
    Map<String, String> langs;
}
