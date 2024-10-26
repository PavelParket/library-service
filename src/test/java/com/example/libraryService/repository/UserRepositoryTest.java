package com.example.libraryService.repository;

import com.example.libraryService.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("New User");
        user.setPassword("Password");
        user.setRole("USER");
    }

    @Test
    @DisplayName("Save")
    @Order(1)
    @Rollback(value = false)
    public void saveUser() {
        User newUser = userRepository.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
        System.out.println(newUser);
    }

    @Test
    @DisplayName("Find by id")
    @Order(2)
    public void findById() {
        Long id = 1L;
        User user = userRepository.findById(id).get();

        System.out.println(user);
        Assertions.assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Find by username")
    @Order(3)
    public void findByUsername() {
        String username = "New User";
        User user = userRepository.findByUsername(username).get();

        System.out.println(user);
        Assertions.assertThat(user.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("Delete")
    @Order(4)
    @Rollback(value = false)
    public void deleteUser() {
        Long id = 1L;
        userRepository.deleteById(id);

        Optional<User> user = userRepository.findById(id);
        Assertions.assertThat(user).isEmpty();
    }
}
