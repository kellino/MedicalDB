package org.ucl.medicaldb;

public class LoginHandler {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String password() {
        return password;
    }

    protected boolean checkLoginDetails() {
        if (this.username == "root" && this.password == "medical") {
            return true;
        }
        return false;
    }

}
