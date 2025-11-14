package online.jeffdev.dev_marketplace.users.identity.domain.models;

public class Password {
    private String hash;

    public Password(String rawPassword) {
        // Hashing logic will be implemented later.
        this.hash = rawPassword;
    }

    public String getHash() {
        return hash;
    }
}
