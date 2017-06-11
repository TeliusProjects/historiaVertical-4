package com.example.admin.prova;

import java.io.Serializable;

/**
 * Created by admin on 04/05/2017.
 */
class LogIn implements Serializable {
    private transient String username;
    private transient String Password;
    private transient String encoded_profileImageBitmap;
    private transient String profileImageName;
    private transient String androidPath;
    private transient String email;


    public LogIn(){

    }

    public LogIn(String username, String Password)
    {
        this.username = username;
        this.Password=Password;
    }

    public void  setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAndroidPath() {
        return androidPath;
    }

    public void setAndroidPath(String androidPath) {
        this.androidPath = androidPath;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return username;
    }

    public String getEncoded_profileImageBitmap() {
        return encoded_profileImageBitmap;
    }

    public void setEncoded_profileImageBitmap(String encoded_profileImageBitmap) {
        this.encoded_profileImageBitmap = encoded_profileImageBitmap;
    }

    public String getProfileImageName() {
        return profileImageName;
    }

    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }
}

