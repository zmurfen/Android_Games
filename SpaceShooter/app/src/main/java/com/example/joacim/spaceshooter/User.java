package com.example.joacim.spaceshooter;

import java.io.Serializable;

/**
 * Created by joacim on 2018-03-30.
 */

public class User implements Serializable{

    private String name, password, email;
    private int id, level, gold;

    public User() {

    }

    public User( int id, String name, String password, String email, int level, int gold) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.level = level;
        this.gold = gold;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }

}
