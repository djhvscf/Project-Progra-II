package com.randomquestions.user;

import java.io.Serializable;

public class UserBean implements Serializable
{
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String Name)
    {
        this.name = Name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "UserBean{" + "id=" + getId() + ", Name=" + getName() + '}';
    }
}
