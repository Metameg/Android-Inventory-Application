package com.alexmetzger.inventoryapplication;

public class Login {
    private String mUsername;
    private String mPassword;
    private long mId;

    public Login() {}
    public Login(String usr, String pwd) {
        mUsername = usr;
        mPassword = pwd;
    }


    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String pwd) {
        mPassword = pwd;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }


}
