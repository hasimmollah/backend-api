package com.nutmeg.service;

import com.nutmeg.entity.User;
import com.nutmeg.model.UserDto;
import com.nutmeg.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldVerifyFetchUser() {
        String userName = "test";
        User user = new User();
        user.setName(userName);
        userRepository.save(user);
        List<UserDto> users = userService.fetchUsers();
        assertThat(users).hasSize(1);
        assertThat(users)
                .extracting(UserDto::getName)
                .containsExactly(userName);
    }
}
