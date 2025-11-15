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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinkedInOAuthProviderTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    void whenFetchingEmailFromProvider_thenReturnsEmail() {
        var provider = new LinkedInOAuthProvider(restTemplate);
        LinkedInEmailResponse mockResponse = new LinkedInEmailResponse("test@example.com", true, true);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(mockResponse);

        Optional<Email> email = provider.getEmailFromProvider("test-code");

        assertTrue(email.isPresent());
        assertEquals(mockResponse.getEmail(), email.get().getValue());
    }
}