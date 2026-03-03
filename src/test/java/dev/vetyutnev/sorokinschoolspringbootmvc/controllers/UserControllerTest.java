package dev.vetyutnev.sorokinschoolspringbootmvc.controllers;

import dev.vetyutnev.sorokinschoolspringbootmvc.entity.User;
import dev.vetyutnev.sorokinschoolspringbootmvc.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("POST /api/users должен вернуть 201 и  создать пользователя")
    void createUser_ShouldReturnCreated_WhenValidRequest() throws Exception {

        User inputUser = new User(null, "Ivan", "ivan@test.com", 25);

        User savedUser = new User(1L, "Ivan", "ivan@test.com", 25);

        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("POST /api/users должен вернуть 400, если данные некорректны")
    void createUser_ShouldReturnBadRequestWhenInvalidData() throws Exception {
        User invalidUser = new User(null, "", "ivantest.com", -25);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/users должен вернуть 200 и пользователя")
    void getUserById_ShouldReturnUserIfExist() throws Exception {
        User existingUser = new User(1L, "Ivan", "ivan@test.com", 25);

        when(userService.getUserById(1L)).thenReturn(existingUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Ivan"));
    }


}