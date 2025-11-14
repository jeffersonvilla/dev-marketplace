package online.jeffdev.dev_marketplace.users.identity.application.ports.in;

import online.jeffdev.dev_marketplace.users.identity.application.dtos.EmailPasswordRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.OAuthRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.UserRegistrationResult;

public interface RegisterUserUseCase {
    UserRegistrationResult registerWithEmailPassword(EmailPasswordRegistration command);
    UserRegistrationResult registerWithOAuth(OAuthRegistration command);
}
