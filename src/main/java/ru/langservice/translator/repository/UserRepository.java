package ru.langservice.translator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.langservice.translator.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();
}
