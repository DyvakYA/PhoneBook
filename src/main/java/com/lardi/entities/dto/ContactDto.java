package com.lardi.entities.dto;

import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class ContactDto {

    @Id
    @Transient
    @NotNull(message = "{contact.firstName.not_null}")
    @NotBlank(message = "{contact.firstName.not_blank}")
    @Size(min = 4, message = "{contact.firstName.wrong_size}")
    private String firstName;

    @Transient
    @NotNull(message = "{contact.lastName.not_null}")
    @NotBlank(message = "{contact.lastName.not_blank}")
    @Size(min = 4, message = "{contact.lastName.wrong_size")
    private String lastName;

    @Transient
    @NotNull(message = "{contact.middleName.not_null}")
    @NotBlank(message = "{contact.middleName.not_blank}")
    @Size(min = 4, message = "{contact.middleName.wrong_size}")
    private String middleName;

    @Transient
    @NotNull(message = "{contact.phoneNumberMobile.not_null}")
    @NotBlank(message = "{contact.phoneNumberMobile.not_blank}")
    @Pattern(regexp = "^((8|\\+3)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "{contact.phoneNumberMobile.regex}")
    private String phoneNumberMobile;

    @Transient
    @Pattern(regexp = "^((8|\\+3)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "{contact.phoneNumberHome.regex}")
    private String phoneNumberHome;

    @Transient
    private String address;

    @Transient
    @Email(message = "contact.email.incorrect_value")
    private String email;

    @Transient
    private Integer userId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactDto that = (ContactDto) o;

        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        if (!middleName.equals(that.middleName)) return false;
        if (!phoneNumberMobile.equals(that.phoneNumberMobile)) return false;
        if (phoneNumberHome != null ? !phoneNumberHome.equals(that.phoneNumberHome) : that.phoneNumberHome != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + phoneNumberMobile.hashCode();
        result = 31 * result + (phoneNumberHome != null ? phoneNumberHome.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phoneNumberMobile='" + phoneNumberMobile + '\'' +
                ", phoneNumberHome='" + phoneNumberHome + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }
}
