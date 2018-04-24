package com.drugapp.Pojos;

/**
 * Created by Shiva on 13-07-2017.
 */




public class RegistrationPojo {


    String type;
    String name;
    String contact;
    String email;
    String password;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getLatlang() {
        return latlang;
    }

    public void setLatlang(String latlang) {
        this.latlang = latlang;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String latlang;
    String fcmKey;
    String uid;
    String address;

    public RegistrationPojo(String type, String name, String contact, String email, String password, String latlang, String fcmKey, String uid,String address)
    {
        this.type = type;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.latlang = latlang;
        this.fcmKey = fcmKey;
        this.uid = uid;
        this.address=address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RegistrationPojo()
    {

    }







}

