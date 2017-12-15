package com.example.yoshikawaakira.bigtask;

/**
 * Created by yoshikawaakira on 2017/12/13.
 */

public class ContactDetail {
    private String name;
    private String email;
    private String pass;
    private String phone;
    private String gender;
    private String dob;
    private String img_path;


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

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPath() {
        return img_path;
    }
    public void setPath(String img_path) {
        this.img_path = img_path;
    }


}
