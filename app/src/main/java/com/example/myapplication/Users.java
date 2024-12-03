package com.example.myapplication;

public class Users {
        public String lastname;
        public String name;
        public String username;
        public String email;
        public String pass;
        public String comfomPass;

        // Constructor mặc định (bắt buộc cho Firebase)
        public Users() {}

        // Constructor có tham số
        public Users(String lastname, String name, String username, String email, String pass, String comfomPass) {
            this.lastname = lastname;
            this.name = name;
            this.username = username;
            this.email = email;
            this.pass = pass;
            this.comfomPass = comfomPass;
        }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getComfomPass() {
        return comfomPass;
    }

    public void setComfomPass(String comfomPass) {
        this.comfomPass = comfomPass;
    }
}

