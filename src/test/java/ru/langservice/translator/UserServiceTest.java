package ru.langservice.translator;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.langservice.translator.domain.Role;
import ru.langservice.translator.domain.User;
import ru.langservice.translator.exception.UserAlreadyExistAuthenticationException;
import ru.langservice.translator.repository.TranslationRepository;
import ru.langservice.translator.repository.UserRepository;
import ru.langservice.translator.service.UserService;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TranslationRepository translationRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUser() {
        Mockito.when(userRepository.findByUsername("user")).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer((Answer<User>) invocationOnMock -> {
                    Object[] args = invocationOnMock.getArguments();
                    return (User) args[0];
                });

        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        User savedUser = userService.addUser(user);

        assertTrue("active", savedUser.isActive());
        assertTrue("enabled", savedUser.isEnabled());
        assertFalse("expired", savedUser.isExpired());
        assertFalse("locked", savedUser.isLocked());

        assertTrue("user roles", CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(user.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).save(savedUser);
    }

    @Test(expected = UserAlreadyExistAuthenticationException.class)
    public void AddUserFail(){

        Mockito.doReturn(new User())
                .when(userRepository)
                .findByUsername("user");

        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        userService.addUser(user);

        Mockito.verify(userRepository, Mockito.times(0)).findByUsername(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any(User.class));
    }
}