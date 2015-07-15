package com.devrebel.inspigen.app.domain.settings;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.devrebel.inspigen.app.domain.common.BaseEntity;

@Entity
@Table(name="ig_system_settings")
public class Settings extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6053786706744243022L;

    private int maxLoginAttempts;
    private int accountLockTime;
    private int linkExpirationTime;
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

    public int getLinkExpirationTime() {
        return linkExpirationTime;
    }

    public void setLinkExpirationTime(int linkExpirationTime) {
        this.linkExpirationTime = linkExpirationTime;
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