package online.jeffdev.dev_marketplace.users.identity.domain.models;

import java.util.UUID;

public class UserIdentity {
    private UUID id;
    private Email email;
    private Password password;
    private ProfileStatus profileStatus;
    private ConfirmationStatus confirmationStatus;
    private ConfirmationToken confirmationToken;

    // Constructor for Email/Password registration
    public UserIdentity(Email email, Password password) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.profileStatus = ProfileStatus.INCOMPLETE;
        this.confirmationStatus = ConfirmationStatus.UNCONFIRMED;
        this.confirmationToken = new ConfirmationToken();
    }

    // Constructor for OAuth registration
    public UserIdentity(Email email) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = null; // No password for OAuth users initially
        this.profileStatus = ProfileStatus.INCOMPLETE;
        this.confirmationStatus = ConfirmationStatus.CONFIRMED; // Email is considered verified by the provider
        this.confirmationToken = null;
    }

    // Empty constructor for persistence frameworks and tests
    public UserIdentity() {
        this.id = UUID.randomUUID();
        this.profileStatus = ProfileStatus.INCOMPLETE;
        this.confirmationStatus = ConfirmationStatus.UNCONFIRMED;
    }

    public UUID getId() {
        return id;
    }

    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    public ConfirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    public ConfirmationToken getConfirmationToken() {
        return confirmationToken;
    }
}
