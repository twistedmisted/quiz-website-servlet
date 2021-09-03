package com.epam.final_project.utils;

public class Validation {

    public static String loginValidation(String login) {
        if (login.isEmpty()) {
            return "It should not be empty";
        }

        if (login.length() < 2 || login.length() > 30) {
            return "It should be from 2 to 30 characters";
        }

        return "";
    }

    public static String passwordValidation(String password) {
        if (password.isEmpty()) {
            return "It should not be empty";
        }

        if (password.length() < 2 || password.length() > 30) {
            return "It should be from 2 to 30 characters";
        }

        return "";
    }
}
