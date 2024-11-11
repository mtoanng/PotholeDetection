package com.example.myapplication;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class LoginRespond implements Serializable {

    @Getter
    @Setter
    public class LoginRespon implements Serializable {
        private String token;
        private  String mess;
        private String firstName;
        private String lastName;


    }

}
