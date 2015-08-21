package tk.wanxie.jdbc.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity                                 // used with hibernate to define as an Model
@DynamicUpdate                          // if is true, only fields with changes will be updated when session.update() is called, otherwise even no change will also call an update statement
@SelectBeforeUpdate                     // select the object from db before update to make it persistent, with @DynamicUpdate only changed columns will be updated, otherwise all columns will be updated even with one column change
@Table(name="user")                     // @Table only change the table name, not the class name, where as @Entity(name="users") also change the class name used in hql
public class User {

                                                        // use @EmbeddedId if thie field is a primary key and is also an object of @Embeddable class
    @Id                                                 // if annotation used for getter, the annotation of other fields must also be in getter, not work with setter
    @GeneratedValue(strategy=GenerationType.AUTO)       // default auto increment
    @Column(name="user_id")
    private int userId;

    @Column(name="username")
    private String username;

    @Embedded                                           // @Embedded here is optional if already defined as @Embeddable in the Address class
    private Address address;

    @Embedded                                                                               // if there is mulitple @Embedded of the same Class, use @AttributeOverrides to redefine the
    @AttributeOverrides({                                                                   // column name for each fields to prevent conflict with another address
        @AttributeOverride(name="street", column=@Column(name="comp_street")),              // name is the name of the class field,
        @AttributeOverride(name="city", column=@Column(name="comp_city")),                  // column is a new @column annotation of the db column name
        @AttributeOverride(name="state", column=@Column(name="comp_state")),
        @AttributeOverride(name="zipcode", column=@Column(name="comp_zip_code"))
    })
    private Address companyAddress;
                                                        // defines a collection of instance of @Embeddable class or basic type, use a separate table to store the collection
    @ElementCollection(fetch=FetchType.EAGER)           // don't use FetchType.EAGER b/c it will load all Emails when the User is initialized, use FetchType.LAZY for only when needed
    @JoinTable(
        name="user_email",                              // change the table name for the email join table, this contains the primary key of the user table
        joinColumns=@JoinColumn(name="user_id")         // change the foreign key column name
    )
    @GenericGenerator(name="hilo-gen", strategy="hilo")
    @CollectionId(                                          // create a primary key for the table
        columns=@Column(name="email_id"),
        generator="hilo-gen",
        type=@Type(type="long")
    )
    private Collection<Email> emails = new ArrayList<>();       // cannot Collection<Email> b/c it doesn't support index if want to have primary key

    @OneToOne                                                   // one to one relation mapping
    @JoinColumn(name="vehicle_id")                              // user table contains the primary key to the vehicle table
    private Vehicle vehicle;

                                                                // similar to @ElementCollection but for @Entity to @Entity not @Entity to @Embeddable,
                                                                // the table mapping is based on the @ManyToOne field property in the CreditCard class, where user is the CreditCard field name
    @OneToMany(mappedBy="user")                                 // whether to use a separate intermediate join table is also depend on the @ManyToOne field if using @JoinTable vs @JoinColumn
    private Collection<CreditCard> creditCard = new ArrayList<>();

    @ManyToMany(fetch=FetchType.LAZY)                           // using Hibernate.initialize(user.getWorkspaces()) when fetching the user for FetchType.LAZY
    //@Fetch(FetchMode.SELECT)                                  // FetchType.EAGER could return multiple time of the same row, so use this to select the distinct row only with FetchType.EAGER
    @JoinTable(
        name="user_workspace",                                      // the name of the intermediate join table
        joinColumns=@JoinColumn(name="user_id"),                 // the column name of the 'many' table, the credit_card table here
        inverseJoinColumns=@JoinColumn(name="wordspace_id")          // the column name of the 'one' table, user table here
    )
    private Collection<Workspace> workspaces = new ArrayList<>();

    @Temporal(TemporalType.DATE)                        // format the date to store in the database, default is timestamp
    @Column(name="join_date")
    private Date joinDate;

    @Lob                                                // by default all string is varchar(255), @Lob will changes to a longtext type, can also use with char and binary type
    private String description;

    @Transient
    private String nickname;                            // if don't want to save this field to database, make it as static or use @Transient

    public User() {                                     // @Entity need a default constructor
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, Address address, Address companyAddress, Date joinDate, String description) {
        this.username = username;
        this.address = address;
        this.companyAddress = companyAddress;
        this.joinDate = joinDate;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username + " from getter";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Collection<CreditCard> getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Collection<CreditCard> creditCard) {
        this.creditCard = creditCard;
    }

    public Collection<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(Collection<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Email> getEmails() {
        return emails;
    }

    public void setEmails(Collection<Email> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", address=" + address +
                ", companyAddress=" + companyAddress +
                ", emails=" + emails +
                ", joinDate=" + joinDate +
                ", description='" + description + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
