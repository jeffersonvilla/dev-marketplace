package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.oauth;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.OAuthProviderPort;

import java.util.Optional;

import org.springframework.web.client.RestTemplate;

public class LinkedInOAuthProvider implements OAuthProviderPort {

    private final RestTemplate restTemplate;

    public LinkedInOAuthProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Email> getEmailFromProvider(String code) {
        // In a real application, you would exchange the 'code' for an access token
        // and then use the access token to call the LinkedIn API.
        // For this example, we'll simulate a direct call to get the email.
        try {
            // Dummy API call - replace with actual LinkedIn API endpoint
            String dummyApiUrl = "http://localhost:8080/mock-linkedin-api/emailAddress?code=" + code;
            LinkedInEmailResponse emailResponses = restTemplate.getForObject(dummyApiUrl, LinkedInEmailResponse.class);

            return Optional.of(new Email(emailResponses.getEmail()));

        } catch (Exception e) {
            // Log error
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
