package com.example.libraryService.service;

import com.example.libraryService.entity.User;
import com.example.libraryService.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "User", "usepass", "USER");
    }

    @Test
    @DisplayName("Load by username")
    @Order(1)
    public void loadByUsername() {
        String name = "User";
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));

        User loadedUser = (User) userService.loadUserByUsername(name);

        Assertions.assertThat(loadedUser.getUsername()).isEqualTo(name);
    }
}
