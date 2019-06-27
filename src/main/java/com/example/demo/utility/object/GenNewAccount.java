package com.example.demo.utility.object;

public class GenNewAccount {
    private String hoVaTen;
    private String userName;
    private String passWord;

    public GenNewAccount() {
    }

    public GenNewAccount(String hoVaTen, String userName, String passWord) {
        this.hoVaTen = hoVaTen;
        this.userName = userName;
        this.passWord = passWord;
    }


    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
