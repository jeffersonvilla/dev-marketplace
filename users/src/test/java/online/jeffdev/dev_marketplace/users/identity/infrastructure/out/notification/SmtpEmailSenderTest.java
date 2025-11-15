package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.notification;

import online.jeffdev.dev_marketplace.users.identity.adapter.out.notification.SmtpEmailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SmtpEmailSenderTest {

    @Mock
    private JavaMailSender mailSender;

    @Test
    void whenSendingConfirmationEmail_thenEmailIsSent() {
        var emailSender = new SmtpEmailSender(mailSender);
        emailSender.sendConfirmationEmail("test@example.com", "test-token");

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    interface JavaMailSender {
        void send(SimpleMailMessage message);
    }

    static class SimpleMailMessage {
        public void setTo(String to) {}
        public void setSubject(String subject) {}
        public void setText(String text) {}
    }
}