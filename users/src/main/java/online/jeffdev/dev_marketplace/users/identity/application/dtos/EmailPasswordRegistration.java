package online.jeffdev.dev_marketplace.users.identity.application.dtos;

public class EmailPasswordRegistration {
    private final String email;
    private final String password;

    public EmailPasswordRegistration(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
