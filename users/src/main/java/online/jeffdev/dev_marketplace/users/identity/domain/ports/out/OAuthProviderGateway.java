package online.jeffdev.dev_marketplace.users.identity.domain.ports.out;

import online.jeffdev.dev_marketplace.users.identity.domain.models.OAuthProvider;
import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;

import java.util.Optional;

public interface OAuthProviderGateway {
    Optional<Email> getEmailFromProvider(OAuthProvider provider, String authorizationCode);
}
