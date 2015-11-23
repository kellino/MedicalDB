package org.ucl.gui;

public class LoginHandler {
    private final String root = "root";
    private final String rootPassword = "medical";
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    boolean checkLoginDetails() {
        if (username.equals(root) && password.equals(rootPassword)) {
            return true;
        }
        return false;
    }
}
