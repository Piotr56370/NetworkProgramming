class User {
    private String email;
    private String password;

    User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getEmail() {
        return this.email;
    }

    String getPassword() {
        return this.password;
    }
}
