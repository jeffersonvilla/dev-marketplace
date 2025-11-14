package online.jeffdev.dev_marketplace.users.identity.domain.ports.out;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.models.UserIdentity;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityRepository {
    Optional<UserIdentity> findById(UUID id);
    Optional<UserIdentity> findByEmail(Email email);
    UserIdentity save(UserIdentity userIdentity);
}
