package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.persistence;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.models.Password;
import online.jeffdev.dev_marketplace.users.identity.domain.models.UserIdentity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class JpaUserIdentityRepositoryTest {

    @Autowired
    private SpringUserIdentityRepository springDataRepository;

    private JpaUserIdentityRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JpaUserIdentityRepository(springDataRepository);
    }

    @Test
    void whenSaveUserIdentity_thenItIsPersisted() {
        var userIdentity = new UserIdentity(new Email("test@example.com"), new Password("password"));
        repository.save(userIdentity);

        var foundEntity = springDataRepository.findById(userIdentity.getId());
        assertTrue(foundEntity.isPresent());
        assertNotNull(foundEntity.get());
    }
}