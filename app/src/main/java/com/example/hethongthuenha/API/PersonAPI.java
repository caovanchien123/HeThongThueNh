package com.example.hethongthuenha.API;

import android.app.Application;

public class PersonAPI extends Application {
    private String uid;
    private String name;
    private String email;
    private static PersonAPI instance;

    public static PersonAPI getInstance() {
        if (instance == null)
            instance = new PersonAPI();
        return instance;
    }

    public PersonAPI() {
    }

    public PersonAPI(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonAPI{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
