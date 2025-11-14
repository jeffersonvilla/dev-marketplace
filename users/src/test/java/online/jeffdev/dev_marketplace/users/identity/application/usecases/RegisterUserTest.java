package online.jeffdev.dev_marketplace.users.identity.application.usecases;

import online.jeffdev.dev_marketplace.users.identity.application.dtos.EmailPasswordRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.OAuthRegistration;
import online.jeffdev.dev_marketplace.users.identity.application.dtos.UserRegistrationResult;
import online.jeffdev.dev_marketplace.users.identity.domain.models.*;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.EmailSender;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.OAuthProviderGateway;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.UserIdentityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserTest {

    @Mock
    private UserIdentityRepository userIdentityRepository;
    @Mock
    private EmailSender emailSender;
    @Mock
    private OAuthProviderGateway oAuthProviderGateway;

    private RegisterUser registerUser;

    @BeforeEach
    void setUp() {
        registerUser = new RegisterUser(userIdentityRepository, emailSender, oAuthProviderGateway);
    }

    @Nested
    @DisplayName("Email/Password Registration")
    class EmailPasswordRegistrationTests {

        @Test
        @DisplayName("Should create user with incomplete profile and unconfirmed email on successful registration")
        void successfulRegistrationCreatesUser() {
            // Given
            var command = new EmailPasswordRegistration("test@example.com", "password123");
            var userIdentity = new UserIdentity(); // Assume constructor sets defaults
            when(userIdentityRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());
            when(userIdentityRepository.save(any(UserIdentity.class))).thenReturn(userIdentity);

            // When
            UserRegistrationResult result = registerUser.registerWithEmailPassword(command);

            // Then
            assertNotNull(result);
            assertTrue(result.isSuccess());
            assertNotNull(result.getUserId());
            assertEquals(ProfileStatus.INCOMPLETE, userIdentity.getProfileStatus());
            assertEquals(ConfirmationStatus.UNCONFIRMED, userIdentity.getConfirmationStatus());
        }

        @Test
        @DisplayName("Should send a confirmation email on successful registration")
        void successfulRegistrationSendsConfirmationEmail() {
            // Given
            var command = new EmailPasswordRegistration("test@example.com", "password123");
            when(userIdentityRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());
            when(userIdentityRepository.save(any(UserIdentity.class))).thenReturn(new UserIdentity());

            // When
            registerUser.registerWithEmailPassword(command);

            // Then
            verify(emailSender).sendConfirmationEmail(any(Email.class), any(String.class));
        }

        @Test
        @DisplayName("Should fail registration if email is already in use")
        void registrationFailsForExistingEmail() {
            // Given
            var command = new EmailPasswordRegistration("existing@example.com", "password123");
            when(userIdentityRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(new UserIdentity()));

            // When
            UserRegistrationResult result = registerUser.registerWithEmailPassword(command);

            // Then
            assertFalse(result.isSuccess());
            assertNull(result.getUserId());
            assertEquals("Email is already in use.", result.getMessage());
        }

        @Test
        @DisplayName("Should fail registration for invalid email format")
        void registrationFailsForInvalidEmail() {
            // Given
            var command = new EmailPasswordRegistration("invalid-email", "password123");

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                registerUser.registerWithEmailPassword(command);
            }, "Expected an exception for invalid email format");
        }
    }

    @Nested
    @DisplayName("OAuth Registration")
    class OAuthRegistrationTests {

        @Test
        @DisplayName("Should create a new user for a new OAuth identity")
        void successfulOAuthRegistrationCreatesUser() {
            // Given
            var command = new OAuthRegistration(OAuthProvider.GITHUB, "auth-code");
            var emailFromProvider = new Email("oauth-user@example.com");
            when(oAuthProviderGateway.getEmailFromProvider(OAuthProvider.GITHUB, "auth-code")).thenReturn(Optional.of(emailFromProvider));
            when(userIdentityRepository.findByEmail(emailFromProvider)).thenReturn(Optional.empty());
            when(userIdentityRepository.save(any(UserIdentity.class))).thenReturn(new UserIdentity());

            // When
            UserRegistrationResult result = registerUser.registerWithOAuth(command);

            // Then
            assertTrue(result.isSuccess());
            assertNotNull(result.getUserId());
        }

        @Test
        @DisplayName("Should mark profile as incomplete for new OAuth user")
        void newOAuthUserHasIncompleteProfile() {
            // Given
            var command = new OAuthRegistration(OAuthProvider.GITHUB, "auth-code");
            var emailFromProvider = new Email("new-oauth-user@example.com");
            var userIdentity = new UserIdentity();
            when(oAuthProviderGateway.getEmailFromProvider(any(), any())).thenReturn(Optional.of(emailFromProvider));
            when(userIdentityRepository.findByEmail(any())).thenReturn(Optional.empty());
            when(userIdentityRepository.save(any())).thenReturn(userIdentity);

            // When
            registerUser.registerWithOAuth(command);

            // Then
            assertEquals(ProfileStatus.INCOMPLETE, userIdentity.getProfileStatus());
        }

        @Test
        @DisplayName("Should return existing user if OAuth email already exists")
        void existingUserIsReturnedForExistingOAuthEmail() {
            // Given
            var command = new OAuthRegistration(OAuthProvider.LINKEDIN, "auth-code");
            var existingEmail = new Email("existing-oauth@example.com");
            var existingUser = new UserIdentity(); // Assume this user has an ID
            when(oAuthProviderGateway.getEmailFromProvider(any(), any())).thenReturn(Optional.of(existingEmail));
            when(userIdentityRepository.findByEmail(existingEmail)).thenReturn(Optional.of(existingUser));

            // When
            UserRegistrationResult result = registerUser.registerWithOAuth(command);

            // Then
            assertTrue(result.isSuccess());
            assertEquals(existingUser.getId(), result.getUserId());
        }
    }
}
