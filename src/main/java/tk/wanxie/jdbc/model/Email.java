package tk.wanxie.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {

    @Column(name="email_address")
    private String emailAddress;

    public Email() {
    }

    public Email(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
