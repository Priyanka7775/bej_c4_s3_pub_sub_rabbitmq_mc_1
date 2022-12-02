package com.example.UserTrackservices.rabbitMq;

public class CommonUser {
    private String userId;
    private String userName;
    private String address;
    private String password;

    public CommonUser(String userId, String userName, String address, String password) {
        this.userId = userId;
        this.userName = userName;
        this.address = address;
        this.password = password;
    }

    public CommonUser(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CommonUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
