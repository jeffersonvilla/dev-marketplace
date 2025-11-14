package online.jeffdev.dev_marketplace.users.identity.domain.models;

public class OAuthIdentity {
    private OAuthProvider provider;
    private String providerUserId;

    public OAuthIdentity(OAuthProvider provider, String providerUserId) {
        this.provider = provider;
        this.providerUserId = providerUserId;
    }

    public OAuthProvider getProvider() {
        return provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }
}
