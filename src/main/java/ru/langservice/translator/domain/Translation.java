package ru.langservice.translator.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id","user"})
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message too long")
    private String text;
    @NotBlank(message = "Please fill the lang")
    @Length(max = 5, message = "Message too long. For example en-ru")
    private String lang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Translation(String text, String lang, User user) {
        this.text = text;
        this.lang = lang;
        this.user = user;
    }
}
