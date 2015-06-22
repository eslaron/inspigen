package org.sobiech.inspigen.core.models.dto;

import java.util.Date;

//Klasa do transportu wybranych informacji o użytkownikach
public class UserDto {

    private Long id;                    //id użytkownika

    private String username;            //nazwa użytkownika

    private String password;            //hasło

    private String email;                //adres email

    private String role;                //rola użytkownika

    private String enabled;                //informacja o aktywności konta(Tak lub Nie)

    private String locked;                //informacja o blokadzie konta(Tak lub Nie)

    private int failedLogins;            //liczba nieudanych prob logowania

    private Date lastLoginAttempt;      //ostatnia nieudana proba logowania

    public UserDto() {
    }

    //Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }

    public Date getLastLoginAttempt() {
        return lastLoginAttempt;
    }

    public void setLastLoginAttempt(Date lastLoginAttempt) {
        this.lastLoginAttempt = lastLoginAttempt;
    }
}