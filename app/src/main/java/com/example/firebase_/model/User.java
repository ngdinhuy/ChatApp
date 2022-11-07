package com.example.firebase_.model;

import java.io.Serializable;

public class User implements Serializable {
    public String name,image,email,token,password;

    public User(String name, String image, String email) {
        this.name = name;
        this.image = image;
        this.email = email;
    }
    public User(String name, String image, String email, String password) {
        this.name = name;
        this.image = image;
        this.email = email;
        this.password = password;
    }
    public User(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
