package online.jeffdev.dev_marketplace.users.identity.domain.ports.out;

import java.util.Optional;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;

public interface OAuthProviderPort {
    Optional<Email> getEmailFromProvider(String code);
}
