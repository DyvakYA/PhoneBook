package com.lardi.entities;

public class JwtUser {

    private User user;
    private String token;

    private JwtUser() {

    }

    public JwtUser(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public static JwtUserBuilder builder() {
        return new JwtUserBuilder();
    }

    public static class JwtUserBuilder {

        private User user;
        private String token;

        public JwtUserBuilder user(User user) {
            this.user = user;
            return this;
        }

        public JwtUserBuilder token(String token) {
            this.token = token;
            return this;
        }

        public JwtUser build() {
            return new JwtUser(this.user, this.token);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JwtUser jwtUser = (JwtUser) o;

        if (user != null ? !user.equals(jwtUser.user) : jwtUser.user != null) return false;
        return token != null ? token.equals(jwtUser.token) : jwtUser.token == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
