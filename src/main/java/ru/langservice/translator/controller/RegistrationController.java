package ru.langservice.translator.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.langservice.translator.domain.Role;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){
        User userFromRepository = userRepository.findByUsername(user.getUsername());
        if (userFromRepository != null ) {
            model.put("message", "user already exists");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
