package ru.langservice.translator.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.exception.UserAlreadyExistAuthenticationException;
import ru.langservice.translator.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAllAttributes(errors);
            return "registration";
        }
        if (user.getPassword() != null && !user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("passwordError", "Password and confirm password aren't the same");
            return "registration";
        }
        try {
            user.setPassword(user.getPassword());
            userService.addUser(user);
        } catch (UserAlreadyExistAuthenticationException e){
            model.addAttribute("usernameError", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }
}
