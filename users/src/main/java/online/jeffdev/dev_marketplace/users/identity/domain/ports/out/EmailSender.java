package online.jeffdev.dev_marketplace.users.identity.domain.ports.out;

import online.jeffdev.dev_marketplace.users.identity.domain.models.Email;

public interface EmailSender {
    void sendConfirmationEmail(Email to, String confirmationToken);
}
