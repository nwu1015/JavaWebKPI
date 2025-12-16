package com.example.spacecatmarket.javawebkpi.config.security;

import com.example.spacecatmarket.javawebkpi.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Security & Authorization Tests")
public class SecurityAccessTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Anonymous user should get 401 on protected API endpoints")
    void shouldDenyAccessToAnonymous() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Access granted with @WithMockUser")
    @WithMockUser(username = "astro-cat", roles = "USER")
    void shouldAllowAccessWithMockUser() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Access granted with Mock GitHub Login")
    void shouldAllowAccessWithGithubLogin() throws Exception {
        mockMvc.perform(get("/products")
                        .with(oauth2Login()
                                .attributes(attrs -> {
                                    attrs.put("login", "octocat");
                                    attrs.put("name", "GitHub User");
                                })
                        ))
                .andExpect(status().isOk());
    }
}