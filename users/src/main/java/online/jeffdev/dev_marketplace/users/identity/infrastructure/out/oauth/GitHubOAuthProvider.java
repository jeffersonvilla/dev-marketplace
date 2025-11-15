package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.oauth;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.OAuthProviderGateway;

import java.util.Optional;

import org.springframework.web.client.RestTemplate;

public class GitHubOAuthProvider implements OAuthProviderGateway {

    private final RestTemplate restTemplate;

    public GitHubOAuthProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Email> getEmailFromProvider(String code) {
        // In a real application, you would exchange the 'code' for an access token
        // and then use the access token to call the GitHub API.
        // For this example, we'll simulate a direct call to get the email.
        try {
            // Dummy API call - replace with actual GitHub API endpoint
            String dummyApiUrl = "http://localhost:8080/mock-github-api/user/emails?code=" + code;
            GitHubEmailResponse[] emailResponses = restTemplate.getForObject(dummyApiUrl, GitHubEmailResponse[].class);

            if (emailResponses != null && emailResponses.length > 0) {
                for (GitHubEmailResponse response : emailResponses) {
                    if (response.isPrimary() && response.isVerified()) {
                        return Optional.of(new Email(response.getEmail()));
                    }
                }
            }
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Helper class to parse GitHub email response
    private static class GitHubEmailResponse {
        private String email;
        private boolean primary;
        private boolean verified;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        public boolean isVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }
    }
}
