package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.oauth;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.OAuthProviderGateway;

import java.util.Optional;

import org.springframework.web.client.RestTemplate;

public class LinkedInOAuthProvider implements OAuthProviderGateway {

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
            LinkedInEmailResponse emailResponse = restTemplate.getForObject(dummyApiUrl, LinkedInEmailResponse.class);

            if (emailResponse != null && emailResponse.getElements() != null && !emailResponse.getElements().isEmpty()) {
                return Optional.of(new Email(emailResponse.getElements().get(0).getHandle().getEmailAddress()));
            }
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Helper classes to parse LinkedIn email response
    private static class LinkedInEmailResponse {
        private java.util.List<Element> elements;

        public java.util.List<Element> getElements() {
            return elements;
        }

        public void setElements(java.util.List<Element> elements) {
            this.elements = elements;
        }
    }

    private static class Element {
        private Handle handle;

        public Handle getHandle() {
            return handle;
        }

        public void setHandle(Handle handle) {
            this.handle = handle;
        }
    }

    private static class Handle {
        private String emailAddress;

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
    }
}
