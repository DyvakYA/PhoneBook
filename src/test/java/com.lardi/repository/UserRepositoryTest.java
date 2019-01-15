package com.lardi.repository;

import com.lardi.config.H2JpaConfig;
import com.lardi.dao.UserRepository;
import com.lardi.entities.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {H2JpaConfig.class})
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    private static final Logger log = Logger.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        user = User.builder()
                .username("admin")
                .password("admin")
                .fullName("admin")
                .build();
        log.info(user);
        userRepository.save(user);
    }

    @Test
    public void whenFindUserByUsername_thenReturnUser() {

        // when
        Optional<User> optional = userRepository.findUserByUsername(user.getUsername());
        User found = optional.get();

        // then
        assertTrue("admin".equals(found.getPassword()));
        assertEquals(found.getUsername(), user.getUsername());
    }

    @Test
    public void whenExistByUserName_thenReturnTrue() {

        // when
        boolean result = userRepository.existsByUserName(user.getUsername());

        // then
        assertTrue(result);
    }
}
