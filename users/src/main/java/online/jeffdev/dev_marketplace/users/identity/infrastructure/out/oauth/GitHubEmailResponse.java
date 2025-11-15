package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.oauth;

public class GitHubEmailResponse {
    private String email;
    private boolean primary;
    private boolean verified;

    public GitHubEmailResponse(String email, boolean primary, boolean verified){
        this.email = email;
        this.primary = primary;
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPrimary() {
        return primary;
    }

    public boolean isVerified() {
        return verified;
    }
}