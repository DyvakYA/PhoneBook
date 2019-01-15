package com.lardi.service;

import com.lardi.dao.UserRepository;
import com.lardi.entities.JwtUser;
import com.lardi.entities.User;
import com.lardi.exception.UserAlreadyExistException;
import com.lardi.exception.UserNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public JwtUser login(String username, String password) {
        log.info(String.format("Try to enter user with - username : %s, and password : %s", username, password));

        if (userRepository.existsByUserName(username)) {
            throw new UserAlreadyExistException("User already exist");
        } else {
            Optional<User> user = userRepository.findUserByUsername(username).filter(s -> (password).equals(s.getPassword()));
            if (user.isPresent()) {
                log.info("user - is present" + user.get());
                return JwtUser.builder()
                        .user(user.get())
                        .token("Access allowed")
                        .build();
            } else {
                throw new UserNotFoundException(String.format("User with login: %s, and password : %s - not found ", username, password));
            }
        }
    }

    @Transactional(readOnly = true)
    public User findUserById(Integer userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException(String.valueOf(userId));
        }
    }

    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }
}
