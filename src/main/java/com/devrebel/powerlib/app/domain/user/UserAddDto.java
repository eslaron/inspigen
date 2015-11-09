package com.devrebel.powerlib.app.domain.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAddDto {

    @NotNull
    @Size(min = 1, max = 30, message = "error.username.size")
    private String username;
    private String password;
    @NotNull
    @Size(min = 1, max = 30, message = "error.username.size")
    private String email;
    private String role;
    private Boolean enabled;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}