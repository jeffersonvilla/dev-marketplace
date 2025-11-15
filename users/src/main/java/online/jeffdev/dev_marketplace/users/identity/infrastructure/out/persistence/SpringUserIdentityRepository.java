package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringUserIdentityRepository extends JpaRepository<UserIdentityJpaEntity, UUID> {
    UserIdentityJpaEntity findByEmail(String email);
}
