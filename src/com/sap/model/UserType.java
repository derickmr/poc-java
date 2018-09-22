package com.sap.model;

public enum UserType {

    USER("USER"),
    ADMIN("ADMIN");

    String userType;

    private UserType (String userType){
        this.userType = userType;
    }

    public String getUserType(){
        return userType;
    }

}
