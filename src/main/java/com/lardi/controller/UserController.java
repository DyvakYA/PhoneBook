package com.lardi.controller;

import com.lardi.entities.JwtUser;
import com.lardi.entities.User;
import com.lardi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/users") // Specified controller path
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> users() {
        log.info("get all products");
        // Invoke the appropriate service method and return
        return userService.findAll();
    }

    @RequestMapping(path="/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody @Valid User user) {
        log.info("get all products");
        userService.create(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public JwtUser login(@RequestBody User user) {
        log.info("LOGIN - " + user);
        return userService.login(user.getUsername(), user.getPassword());
    }

}
