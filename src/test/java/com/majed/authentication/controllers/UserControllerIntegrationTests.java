package com.majed.authentication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.majed.authentication.TestDataUtil;
import com.majed.authentication.domain.entities.UserEntity;
import com.majed.authentication.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    private UserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTests(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.userService = userService;
    }

    @Test
    public void testThatCreateUserSuccessfullyReturns201Created() throws Exception {
        UserEntity testUserA = TestDataUtil.createTestUserA();
        testUserA.setId(null);
        String userJson = objectMapper.writeValueAsString
                (testUserA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {
        UserEntity testUserA = TestDataUtil.createTestUserA();
        testUserA.setId(null);
        String userJson = objectMapper.writeValueAsString
                (testUserA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Majed")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("majed@skiff.com")
        );
    }

    @Test
    public void testThatListUsersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListUsersReturnsListOfUsers() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserA();
        userService.createUser(testUserEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("content.[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content.[0].name").value("Majed")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content.[0].email").value("majed@skiff.com")
        );
    }

    @Test
    public void testThatGetUsersReturnsHttpStatus200WhenUserExist() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserA();
        userService.createUser(testUserEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetUsersReturnsHttpStatus404WhenNoUserExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetUsersReturnsUserWhenUserExist() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserA();
        userService.createUser(testUserEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Majed")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("majed@skiff.com")
        );
    }

    @Test
    public void testThatDeleteUserReturnsHttpStatus200() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserA();
        userService.createUser(testUserEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatDeleteUserReturnsHttpStatus404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateUserReturnsHttpStatus200() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserA();
        userService.createUser(testUserEntityA);
        String userJson = objectMapper.writeValueAsString
                (testUserEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateUserNoExistsReturnsHttpStatus404() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserA();
        String userJson = objectMapper.writeValueAsString
                (testUserEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/1").contentType(MediaType.APPLICATION_JSON).content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
