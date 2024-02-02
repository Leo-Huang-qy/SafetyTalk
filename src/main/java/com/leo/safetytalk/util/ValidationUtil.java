package com.leo.safetytalk.util;

public class ValidationUtil {

    public static boolean isPasswordSecure(String password) {
        // check minimum length, presence of uppercase letters, numbers, etc.
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*\\d.*");
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidUsername(String username) {
        return username.length() >= 6;
    }

}
