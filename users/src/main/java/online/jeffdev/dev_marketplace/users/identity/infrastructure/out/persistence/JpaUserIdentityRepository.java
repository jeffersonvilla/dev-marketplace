package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.persistence;

import online.jeffdev.dev_marketplace.users.identity.domain.models.UserIdentity;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.UserIdentityRepository;
import online.jeffdev.dev_marketplace.users.identity.infrastructure.out.persistence.SpringUserIdentityRepository;
import online.jeffdev.dev_marketplace.users.identity.infrastructure.out.persistence.UserIdentityJpaEntity;

import java.util.Optional;
import java.util.UUID;

public class JpaUserIdentityRepository implements UserIdentityRepository {

    private final SpringUserIdentityRepository springDataRepository;

    public JpaUserIdentityRepository(SpringUserIdentityRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public UserIdentity save(UserIdentity userIdentity) {
        UserIdentityJpaEntity entity = toJpaEntity(userIdentity);
        UserIdentityJpaEntity savedEntity = springDataRepository.save(entity);
        return toDomainModel(savedEntity);
    }

    @Override
    public Optional<UserIdentity> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomainModel);
    }

    @Override
    public Optional<UserIdentity> findByEmail(String email) {
        return Optional.ofNullable(springDataRepository.findByEmail(email)).map(this::toDomainModel);
    }

    private UserIdentityJpaEntity toJpaEntity(UserIdentity userIdentity) {
        UserIdentityJpaEntity entity = new UserIdentityJpaEntity();
        entity.setId(userIdentity.getId());
        entity.setEmail(userIdentity.getEmail().getValue());
        entity.setPassword(userIdentity.getPassword() != null ? userIdentity.getPassword().getValue() : null);
        // TODO: Map other fields like profileStatus, confirmationStatus, confirmationToken
        return entity;
    }

    private UserIdentity toDomainModel(UserIdentityJpaEntity entity) {
        // This is a simplified conversion. In a real application, you'd have more robust
        // logic to reconstruct the UserIdentity with its value objects.
        // For now, we'll just create a new UserIdentity with the email.
        return new UserIdentity(new online.jeffdev.dev_marketplace.users.identity.domain.models.Email(entity.getEmail()));
    }
}
