package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class UserIdentityJpaEntity {

    @Id
    private UUID id;
    private String email;
    private String password;
    // Other fields...

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
