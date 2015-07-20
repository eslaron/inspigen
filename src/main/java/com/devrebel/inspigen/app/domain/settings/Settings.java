package com.devrebel.inspigen.app.domain.settings;

import com.devrebel.inspigen.core.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="ig_system_settings")
public class Settings extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -6053786706744243022L;

    private int maxLoginAttempts;
    private int accountLockTime;
    private int urlExpirationTime;
    private String emailAddress;
    private String emailHost;
    private int emailPort;
    private String emailUsername;
    private String emailPassword;

    public Settings() {}

    public int getMaxLoginAttempts() {
        return maxLoginAttempts;
    }

    public void setMaxLoginAttempts(int maxLoginAttempts) {
        this.maxLoginAttempts = maxLoginAttempts;
    }

    public int getAccountLockTime() {
        return accountLockTime;
    }

    public void setAccountLockTime(int accountLockTime) {
        this.accountLockTime = accountLockTime;
    }

    public int getUrlExpirationTime() {
        return urlExpirationTime;
    }

    public void setUrlExpirationTime(int urlExpirationTime) {
        this.urlExpirationTime = urlExpirationTime;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public int getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(int emailPort) {
        this.emailPort = emailPort;
    }

    public String getEmailUsername() {
        return emailUsername;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }
}