package com.example.user_spring;

import com.example.user_spring.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JWTUtilTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtUtil;

    private String token;

    @BeforeAll
    void setUp() {
        token = jwtUtil.generateToken("admin");
    }

    @Test
    void testLoginReturnsToken() throws Exception {
        String requestBody = """
    {
        "username": "testUser",
        "password": "password123"
    }
    """;

        MvcResult result = mockMvc.perform(post("/api/auth/login") // Simula richiesta login
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk()) // Deve restituire 200 OK
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("token")); // Verifica che la risposta contenga un token JWT
    }

    @Test
    void testValidTokenReturns200() throws Exception {
        mockMvc.perform(get("/api/auth")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()); // Deve restituire 200 OK
    }

    @Test
    void testInvalidTokenReturns401() throws Exception {
        mockMvc.perform(get("/api/auth")
                        .header("Authorization", "Bearer invalidToken"))
                .andExpect(status().isUnauthorized()); // Deve restituire 401 Unauthorized
    }

    @Test
    void testExpiredTokenReturns403() throws Exception {
        String expiredToken = jwtUtil.generateToken("expiredUser");

        mockMvc.perform(get("/api/auth")
                        .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isForbidden()); // Deve restituire 403 Forbidden
    }
}
