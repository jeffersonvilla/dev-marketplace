package online.jeffdev.dev_marketplace.users.identity.application.dtos;

import java.util.UUID;

public class UserRegistrationResult {
    private final UUID userId;
    private final boolean success;
    private final String message;

    public UserRegistrationResult(UUID userId, boolean success, String message) {
        this.userId = userId;
        this.success = success;
        this.message = message;
    }

    public UUID getUserId() {
        return userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
