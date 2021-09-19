package com.epam.final_project.dao.model;

public class User {

    private long id;

    private String login;

    private String password;

    private String accessLevel;

    private String email;

    public static User createUser(String email, String login, String password, String accessLevel) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setAccessLevel(accessLevel);
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User: " + id + " " + login + " " + email + " " + password;
    }
}
