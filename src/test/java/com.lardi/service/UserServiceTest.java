package com.lardi.service;

import com.lardi.dao.UserRepository;
import com.lardi.entities.User;
import com.lardi.exception.UserNotFoundException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class UserServiceTest {

    private static final Logger log = Logger.getLogger(UserServiceTest.class);

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService service;

    private List<User> users;

    @Before
    public void setUp() {
        User user = User.builder()
                .id(1)
                .username("admin")
                .password("admin")
                .fullName("admin")
                .build();
        users = new ArrayList<>();
        users.add(user);

        when(userRepository.findUserByUsername("admin"))
                .thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(users);

        when(userRepository.findAllById(any())).thenReturn(users);
    }

    @Test
    public void userServiceTest_whenLoginExecute() {
        service.login("admin", "admin");
        verify(userRepository,times(1)).findUserByUsername("admin");
    }

    @Test
    public void findAllTest() {
        List<User> result = service.findAll();
        verify(userRepository).findAll();
        assertEquals(result, users);
    }

    @Test(expected = UserNotFoundException.class)
    public void findUserById(){
        List<User> result = (List<User>) service.findUserById(any());
        verify(userRepository).findById(any());

    }
}
