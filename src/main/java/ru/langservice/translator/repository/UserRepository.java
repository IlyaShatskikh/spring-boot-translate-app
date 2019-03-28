package ru.langservice.translator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.langservice.translator.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
