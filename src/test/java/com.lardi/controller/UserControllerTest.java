package com.lardi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lardi.entities.JwtUser;
import com.lardi.entities.User;
import com.lardi.service.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final Logger log = Logger.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User user;

    @Before
    public void setUp() {

        user = User.builder()
                .username("admin")
                .password("admin")
                .fullName("admin")
                .build();
    }


    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray()
            throws Exception {

        List<User> users = Arrays.asList(user);

        given(userService.findAll()).willReturn(users);

        mvc.perform(get("/api/users")
                .header("Authorization", "Access allowed")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].password", is(user.getPassword())))
                .andExpect(jsonPath("$[0].fullName", is(user.getFullName())));
        verify(userService).findAll();
    }

    @Test
    public void whenPostUserForLogin_thenReturnJwtUser()
            throws Exception {

        JwtUser jwt = JwtUser.builder()
                .user(user)
                .token("Good token")
                .build();

        given(userService.login("admin", "admin")).willReturn(jwt);

        mvc.perform(post("/api/users/login")
                .header("Authorization", "Access allowed")
                .content(asJsonBytes(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().bytes(asJsonBytes(jwt)));

        verify(userService).login(any(), any());
    }


    private byte[] asJsonBytes(Object object) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
