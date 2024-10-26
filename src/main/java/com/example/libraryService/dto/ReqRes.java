package com.example.libraryService.dto;

import com.example.libraryService.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {
    private int statusCode;

    private String username;

    private String password;

    private String token;

    private String expirationTime;

    private String error;

    private String message;

    private String role;

    private User user;

    public ReqRes(int statusCode, String username, String password, String token, String expirationTime, String error, String message, String role, User user) {
        this.statusCode = statusCode;
        this.username = username;
        this.password = password;
        this.token = token;
        this.expirationTime = expirationTime;
        this.error = error;
        this.message = message;
        this.role = role;
        this.user = user;
    }

    public ReqRes() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReqRes{" +
                "status code='" + statusCode + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", expiration time='" + expirationTime + '\'' +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", role='" + role + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
