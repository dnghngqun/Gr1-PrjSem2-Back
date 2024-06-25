package com.t2307m.group1.prjsem2backend.model;

public class LoginRequest {
    private String identify;
    private String password;

    public LoginRequest(String identify, String password) {
        this.identify = identify;
        this.password = password;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
