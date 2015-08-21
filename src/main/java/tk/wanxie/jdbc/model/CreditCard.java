package tk.wanxie.jdbc.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@NamedQuery(name="CreditCard.byId", query="from CreditCard where cardId = :card_id")           // can be used for common query, best practice to use Entity name for name
@Table(name="credit_card")
public class CreditCard {

    @Id
    @GeneratedValue
    @Column(name="card_id")
    private int cardId;

    @Column(name="card_number")
    private String cardNumber;

                                                                // CascadeType.ALL is to save the collection to database when parent is saved, by default is done manually
                                                                // if don't want to use intermediate join table, remove @JoinTable and use @JoinColumn(name="user_id"),
    @ManyToOne(cascade={CascadeType.ALL})                       // so credit_card table will have a user_id column for joining the user table instead of using an extra table
    @JoinTable(
        name="user_credit_card",                                // the name of the intermediate join table
        joinColumns=@JoinColumn(name="card_id"),                // the column name of the 'many' table, the credit_card table here
        inverseJoinColumns=@JoinColumn(name="user_id")          // the column name of the 'one' table, user table here
    )
    @NotFound(action=NotFoundAction.IGNORE)                    // if the credit card don't have a user, just ignore and don't throw an exception
    private User user;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardId=" + cardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", user=" + user +
                '}';
    }
}
