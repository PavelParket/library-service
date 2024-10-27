package com.example.libraryService.controller;

import com.example.libraryService.config.TestConfig;
import com.example.libraryService.dto.ReqRes;
import com.example.libraryService.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReqRes request;

    @BeforeEach
    public void setUp() {
        request = new ReqRes();
        request.setUsername("User");
        request.setPassword("use");
        request.setRole("USER");
    }

    @Test
    @DisplayName("Sign up")
    @Order(1)
    public void signUp() throws Exception {
        Mockito.when(authService.signUp(Mockito.any(ReqRes.class))).then(invocationOnMock -> {
            ReqRes request = invocationOnMock.getArgument(0);
            ReqRes response = new ReqRes();
            response.setUsername(request.getUsername());
            response.setPassword(request.getPassword());
            response.setStatusCode(201);
            response.setMessage("User saved successfully");
            return response;
        });

        mockMvc.perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(request.getUsername())))
                .andExpect(jsonPath("$.password", is(request.getPassword())));
    }

    @Test
    @DisplayName("Sign in")
    @Order(2)
    public void signIn() throws Exception {
        Mockito.when(authService.signIn(Mockito.any(ReqRes.class))).then(invocationOnMock -> {
            ReqRes request = invocationOnMock.getArgument(0);
            ReqRes response = new ReqRes();
            if (request.getUsername().equals("User") && request.getPassword().equals("use") && request.getRole().equals("USER")) {
                response.setUsername(request.getUsername());
                response.setPassword(request.getPassword());
                response.setStatusCode(200);
                response.setToken("token");
                response.setExpirationTime("5 min");
                response.setMessage("Successfully signed in");
            } else {
                response.setStatusCode(404);
                response.setMessage("Not found");
            }
            return response;
        });

        mockMvc.perform(
                        post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(request.getUsername())))
                .andExpect(jsonPath("$.password", is(request.getPassword())))
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.token", is("token")))
                .andExpect(jsonPath("$.expirationTime", is("5 min")))
                .andExpect(jsonPath("$.message", is("Successfully signed in")));
    }

    @Test
    @DisplayName("Refresh")
    @Order(3)
    public void refresh() throws Exception {
        request.setToken("token");
        Mockito.when(authService.refreshToken(Mockito.any(ReqRes.class))).then(invocationOnMock -> {
            ReqRes request = invocationOnMock.getArgument(0);
            ReqRes response = new ReqRes();
            if (request.getUsername().equals("User") && request.getToken().equals("token")) {
                response.setUsername(request.getUsername());
                response.setPassword(request.getPassword());
                response.setStatusCode(200);
                response.setToken("new token");
                response.setExpirationTime("5 min");
                response.setMessage("Token was refreshed successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Not found");
            }
            return response;
        });

        mockMvc.perform(
                        post("/api/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(request.getUsername())))
                .andExpect(jsonPath("$.password", is(request.getPassword())))
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.token", is("new token")))
                .andExpect(jsonPath("$.expirationTime", is("5 min")))
                .andExpect(jsonPath("$.message", is("Token was refreshed successfully")));
    }
}
