package online.jeffdev.dev_marketplace.users.identity.application.usecases;

import online.jeffdev.dev_marketplace.users.identity.application.dtos.EmailPasswordRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.OAuthRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.UserRegistrationResult;
import online.jeffdev.dev_marketplace.users.identity.application.ports.in.RegisterUserUseCase;
import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.models.Password;
import online.jeffdev.dev_marketplace.users.identity.domain.models.UserIdentity;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.EmailSender;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.OAuthProviderGateway;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.UserIdentityRepository;

import java.util.Optional;

public class RegisterUser implements RegisterUserUseCase {

    private final UserIdentityRepository userIdentityRepository;
    private final EmailSender emailSender;
    private final OAuthProviderGateway oAuthProviderGateway;

    public RegisterUser(UserIdentityRepository userIdentityRepository, EmailSender emailSender, OAuthProviderGateway oAuthProviderGateway) {
        this.userIdentityRepository = userIdentityRepository;
        this.emailSender = emailSender;
        this.oAuthProviderGateway = oAuthProviderGateway;
    }

    @Override
    public UserRegistrationResult registerWithEmailPassword(EmailPasswordRegistration command) {
        Email email = new Email(command.getEmail());
        if (userIdentityRepository.findByEmail(email).isPresent()) {
            return new UserRegistrationResult(null, false, "Email is already in use.");
        }

        Password password = new Password(command.getPassword());
        UserIdentity newUser = new UserIdentity(email, password);
        UserIdentity savedUser = userIdentityRepository.save(newUser);

        // In a real app, a token would be generated and sent.
        emailSender.sendConfirmationEmail(email, "confirmation-token");

        return new UserRegistrationResult(savedUser.getId(), true, "User registered successfully.");
    }

    @Override
    public UserRegistrationResult registerWithOAuth(OAuthRegistration command) {
        Optional<Email> emailOpt = oAuthProviderGateway.getEmailFromProvider(command.getProvider(), command.getAuthorizationCode());

        if (emailOpt.isEmpty()) {
            return new UserRegistrationResult(null, false, "Could not retrieve email from provider.");
        }

        Email email = emailOpt.get();
        Optional<UserIdentity> existingUserOpt = userIdentityRepository.findByEmail(email);

        if (existingUserOpt.isPresent()) {
            return new UserRegistrationResult(existingUserOpt.get().getId(), true, "User already exists.");
        }

        UserIdentity newUser = new UserIdentity(email);
        UserIdentity savedUser = userIdentityRepository.save(newUser);

        return new UserRegistrationResult(savedUser.getId(), true, "User registered successfully.");
    }
}
