package online.jeffdev.dev_marketplace.users.identity.infrastructure.out.notification;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;
import online.jeffdev.dev_marketplace.users.identity.domain.ports.out.EmailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailSender implements EmailSender {

    private final JavaMailSender mailSender;

    public SmtpEmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendConfirmationEmail(Email to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to.getValue());
        message.setSubject("Confirm your email address");
        message.setText("Please click the following link to confirm your email: http://localhost:8080/confirm?token=" + token);
        mailSender.send(message);
    }
}
