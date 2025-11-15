package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.oauth;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GitHubOAuthProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    void whenFetchingEmailFromProvider_thenReturnsEmail() {
        var provider = new GitHubOAuthProvider(restTemplate);
        var mockResponse = new Object() {
            public String email = "test@example.com";
        };
        when(restTemplate.getForObject(anyString(), eq(Object.class))).thenReturn(mockResponse);

        Optional<Email> email = provider.getEmailFromProvider("test-code");

        assertTrue(email.isPresent());
        assertEquals("test@example.com", email.get().getValue());
    }
}