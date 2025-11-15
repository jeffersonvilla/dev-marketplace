package online.jeffdev.dev_marketplace.users.identity.infrastructure.in.web;

import online.jeffdev.dev_marketplace.users.identity.application.dtos.EmailPasswordRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.OAuthRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.UserRegistrationResult;
import online.jeffdev.dev_marketplace.users.identity.application.ports.in.RegisterUserUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class UserRegistrationController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserRegistrationController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/email")
    public UserRegistrationResult registerWithEmailPassword(@RequestBody EmailPasswordRegistration registration) {
        return registerUserUseCase.registerWithEmailPassword(registration);
    }

    @PostMapping("/oauth")
    public UserRegistrationResult registerWithOAuth(@RequestBody OAuthRegistration registration) {
        return registerUserUseCase.registerWithOAuth(registration);
    }
}
