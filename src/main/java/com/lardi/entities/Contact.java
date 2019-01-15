package com.lardi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    @NotNull(message = "{contact.firstName.not_null}")
    @NotBlank(message = "{contact.firstName.not_blank}")
    @Size(min = 4, message = "{contact.firstName.wrong_size}")
    private String firstName;

    @Column(name = "lastName")
    @NotNull(message = "{contact.lastName.not_null}")
    @NotBlank(message = "{contact.lastName.not_blank}")
    @Size(min = 4, message = "{contact.lastName.wrong_size")
    private String lastName;

    @Column(name = "middleName")
    @NotNull(message = "{contact.middleName.not_null}")
    @NotBlank(message = "{contact.middleName.not_blank}")
    @Size(min = 4, message = "{contact.middleName.wrong_size}")
    private String middleName;

    @Column(name = "phoneNumberMobile")
    @NotNull(message = "{contact.phoneNumberMobile.not_null}")
    @NotBlank(message = "{contact.phoneNumberMobile.not_blank}")
    @Pattern(regexp = "^((8|\\+3)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "{contact.phoneNumberMobile.regex}")
    private String phoneNumberMobile;

    @Column(name = "phoneNumberHome")
    @Pattern(regexp = "^((8|\\+3)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "{contact.phoneNumberHome.regex}")
    private String phoneNumberHome;

    @Column(name = "address")
    private String address;

    @Email(message = "contact.email.incorrect_value")
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userId")
    private User user;

    public Contact() {

    }

    public Contact(String lastName, String firstName, String middleName, String phoneNumberMobile, String phoneNumberHome, String address, String email, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumberMobile = phoneNumberMobile;
        this.phoneNumberHome = phoneNumberHome;
        this.address = address;
        this.email = email;
        this.user = user;
    }

    public Contact(int id, String lastName, String firstName, String middleName, String phoneNumberMobile, String phoneNumberHome, String address, String email, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phoneNumberMobile = phoneNumberMobile;
        this.phoneNumberHome = phoneNumberHome;
        this.address = address;
        this.email = email;
        this.user = user;
    }

    public static ContactBuilder builder() {
        return new ContactBuilder();
    }

    public static class ContactBuilder {

        private Integer id;
        private String lastName;
        private String firstName;
        private String middleName;
        private String phoneNumberMobile;
        private String phoneNumberHome;
        private String address;
        private String email;
        private User user;

        public ContactBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ContactBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContactBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContactBuilder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public ContactBuilder phoneNumberMobile(String phoneNumberMobile) {
            this.phoneNumberMobile = phoneNumberMobile;
            return this;
        }

        public ContactBuilder phoneNumberHome(String phoneNumberHome) {
            this.phoneNumberHome = phoneNumberHome;
            return this;
        }

        public ContactBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ContactBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ContactBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Contact build() {
            return new Contact(this.lastName, this.firstName, this.middleName, this.phoneNumberMobile, this.phoneNumberHome, this.address, this.email, this.user);
        }

//        public Contact build() {
//            return new Contact(this.id, this.lastName, this.firstName, this.middleName, this.phoneNumberMobile, this.phoneNumberHome, this.address, this.email, this.user);
//        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumberMobile() {
        return phoneNumberMobile;
    }

    public void setPhoneNumberMobile(String phoneNumberMobile) {
        this.phoneNumberMobile = phoneNumberMobile;
    }

    public String getPhoneNumberHome() {
        return phoneNumberHome;
    }

    public void setPhoneNumberHome(String phoneNumberHome) {
        this.phoneNumberHome = phoneNumberHome;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (!lastName.equals(contact.lastName)) return false;
        if (!firstName.equals(contact.firstName)) return false;
        if (!middleName.equals(contact.middleName)) return false;
        if (!phoneNumberMobile.equals(contact.phoneNumberMobile)) return false;
        if (!phoneNumberHome.equals(contact.phoneNumberHome)) return false;
        if (!address.equals(contact.address)) return false;
        if (!email.equals(contact.email)) return false;
        return user.equals(contact.user);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + phoneNumberMobile.hashCode();
        result = 31 * result + phoneNumberHome.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phoneNumberMobile='" + phoneNumberMobile + '\'' +
                ", phoneNumberHome='" + phoneNumberHome + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", user=" + user +
                '}';
    }
}
