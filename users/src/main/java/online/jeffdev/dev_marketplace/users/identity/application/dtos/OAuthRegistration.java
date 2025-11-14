package online.jeffdev.dev_marketplace.users.identity.application.dtos;

import online.jeffdev.dev_marketplace.users.identity.domain.models.OAuthProvider;

public class OAuthRegistration {
    private final OAuthProvider provider;
    private final String authorizationCode;

    public OAuthRegistration(OAuthProvider provider, String authorizationCode) {
        this.provider = provider;
        this.authorizationCode = authorizationCode;
    }

    public OAuthProvider getProvider() {
        return provider;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }
}
