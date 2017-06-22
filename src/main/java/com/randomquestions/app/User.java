package com.randomquestions.app;

import java.sql.SQLException;

public class User extends Entity {

    private String name;
    private String username;
    private String password;
    private String email;

    public User(long id, String name, String username, String password, String email) {
        super(id);
        this.setEmail(email);
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public void save() throws SQLException {
        super.save();
    }
}
