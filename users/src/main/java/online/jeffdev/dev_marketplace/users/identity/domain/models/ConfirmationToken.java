package online.jeffdev.dev_marketplace.users.identity.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConfirmationToken {

    private final String token;
    private final LocalDateTime expiresAt;

    public ConfirmationToken() {
        this.token = UUID.randomUUID().toString();
        this.expiresAt = LocalDateTime.now().plusDays(1);
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
