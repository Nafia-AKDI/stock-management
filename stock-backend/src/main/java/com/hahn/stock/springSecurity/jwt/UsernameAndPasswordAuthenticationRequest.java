package com.hahn.stock.springSecurity.jwt;

public class UsernameAndPasswordAuthenticationRequest {
    private String email;
    private String password;

    public UsernameAndPasswordAuthenticationRequest() {
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
