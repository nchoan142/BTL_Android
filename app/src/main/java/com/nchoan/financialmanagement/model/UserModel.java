package com.nchoan.financialmanagement.model;

public class UserModel {
    private String userName;
    private String userPassword;
    private String userEmail;

    public UserModel(String userName, String userPassword, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
