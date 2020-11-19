package com.example.hethongthuenha.Model;

import android.app.Application;

public class  Person{
    private String uid;
    private String fullName;
    private String email;
    private String contact;
    private String url;


    public Person() {
    }

    public Person(String uid, String fullName, String email, String contact,String url) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.contact = contact;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Person{" +
                "uid='" + uid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
