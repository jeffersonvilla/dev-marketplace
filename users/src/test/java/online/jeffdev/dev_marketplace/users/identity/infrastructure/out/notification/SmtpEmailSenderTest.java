package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SmtpEmailSenderTest {

    @Mock
    private org.springframework.mail.javamail.JavaMailSender mailSender;

    @Test
    void whenSendingConfirmationEmail_thenEmailIsSent() {
        var emailSender = new SmtpEmailSender(mailSender);
        emailSender.sendConfirmationEmail(new Email("test@example.com"), "test-token");

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

}