package ru.langservice.translator.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.langservice.translator.domain.Role;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.exception.UserAlreadyExistAuthenticationException;
import ru.langservice.translator.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("User by username '{}' not found", username);
            throw new UsernameNotFoundException("User (username = " + username + ") not found");
        }
        return user;
    }

    public User addUser(User user){
        User userFromRepository = userRepository.findByUsername(user.getUsername());
        if (userFromRepository != null ) {
            log.debug("User already exists. Username: {}", user.getUsername());
            throw new UserAlreadyExistAuthenticationException("User (username = "+ user.getUsername() +") already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setExpired(false);
        user.setLocked(false);
        user.setRoles(Collections.singleton(Role.USER));

        log.debug("Saving user: {}", user);
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        user.getRoles().clear();
        log.debug("Add new user (username = {})", username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        log.trace("All available roles: {}", roles);

        form.keySet().stream()
                .filter(roles::contains)
                .map(Role::valueOf)
                .forEach(s -> user.getRoles().add(s));
        log.debug("User (username = {}) roles: {}", user.getUsername(), user.getRoles());

        log.trace("Save user: {}", user);
        return userRepository.save(user);
    }
}
