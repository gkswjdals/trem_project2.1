package main;

public class Admin {
    private String password;

    public Admin(String password) {
        this.password = password;
    }

    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}