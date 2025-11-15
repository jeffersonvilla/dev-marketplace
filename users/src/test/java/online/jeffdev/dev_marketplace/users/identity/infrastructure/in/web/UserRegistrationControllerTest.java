package online.jeffdev.dev_marketplace.users.identity.infrastructure.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.EmailPasswordRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.OAuthRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.UserRegistrationResult;
import online.jeffdev.dev_marketplace.users.identity.domain.models.OAuthProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private online.jeffdev.dev_marketplace.users.identity.application.ports.in.RegisterUserUseCase registerUserUseCase;

    @Test
    void whenRegisteringWithEmailAndPassword_thenReturnsSuccess() throws Exception {
        var registration = new EmailPasswordRegistration("test@example.com", "password");
        var result = new UserRegistrationResult(true, UUID.randomUUID(), "Success");
        when(registerUserUseCase.registerWithEmailPassword(any(EmailPasswordRegistration.class))).thenReturn(result);

        mockMvc.perform(post("/register/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.userId").value(result.getUserId().toString()));
    }

    @Test
    void whenRegisteringWithOAuth_thenReturnsSuccess() throws Exception {
        var registration = new OAuthRegistration(OAuthProvider.GITHUB, "test-code");
        var result = new UserRegistrationResult(true, UUID.randomUUID(), "Success");
        when(registerUserUseCase.registerWithOAuth(any(OAuthRegistration.class))).thenReturn(result);

        mockMvc.perform(post("/register/oauth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.userId").value(result.getUserId().toString()));
    }
}
