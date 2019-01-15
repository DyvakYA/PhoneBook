package com.lardi.entities.dto;

public class SearchDto {

    private String firstName;
    private String lastName;
    private String phoneNumberMobile;
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

    public String getPhoneNumberMobile() {
        return phoneNumberMobile;
    }

    public void setPhoneNumberMobile(String phoneNumberMobile) {
        this.phoneNumberMobile = phoneNumberMobile;
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

        SearchDto searchDto = (SearchDto) o;

        if (firstName != null ? !firstName.equals(searchDto.firstName) : searchDto.firstName != null) return false;
        if (lastName != null ? !lastName.equals(searchDto.lastName) : searchDto.lastName != null) return false;
        if (phoneNumberMobile != null ? !phoneNumberMobile.equals(searchDto.phoneNumberMobile) : searchDto.phoneNumberMobile != null)
            return false;
        return userId != null ? userId.equals(searchDto.userId) : searchDto.userId == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumberMobile != null ? phoneNumberMobile.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumberMobile='" + phoneNumberMobile + '\'' +
                ", userId=" + userId +
                '}';
    }
}
