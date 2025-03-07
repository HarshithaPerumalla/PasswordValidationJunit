package com.password;

public class PasswordValidator {

    private PasswordService passwordService;

    public PasswordValidator(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return !passwordService.isPasswordInBlacklist(password);
    }
}