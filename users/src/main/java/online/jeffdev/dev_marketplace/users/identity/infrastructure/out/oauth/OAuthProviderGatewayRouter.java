package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.oauth;

import org.springframework.stereotype.Component;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.models.OAuthProvider;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.OAuthProviderGateway;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.OAuthProviderPort;

import java.util.Map;
import java.util.Optional;

/**
 * Router adapter that delegates OAuth requests to the appropriate provider-specific adapter.
 * This follows the Adapter pattern in hexagonal architecture, routing between multiple OAuth implementations.
 */
@Component
public class OAuthProviderGatewayRouter implements OAuthProviderGateway {

    private final Map<OAuthProvider, OAuthProviderPort> adapters;

    public OAuthProviderGatewayRouter(GitHubOAuthProvider gitHubAdapter, LinkedInOAuthProvider linkedInAdapter) {
        this.adapters = Map.of(
            OAuthProvider.GITHUB, gitHubAdapter,
            OAuthProvider.LINKEDIN, linkedInAdapter
        );
    }

    @Override
    public Optional<Email> getEmailFromProvider(OAuthProvider provider, String authorizationCode) {
        OAuthProviderPort adapter = adapters.get(provider);

        if (adapter == null) {
            throw new IllegalArgumentException("Unsupported provider: " + provider);
        }

        return adapter.getEmailFromProvider(authorizationCode);
    }
}
