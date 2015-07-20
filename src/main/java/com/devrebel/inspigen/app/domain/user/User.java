package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.common.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ig_users")
public class User extends AbstractEntity implements UserDetails {

    private static final long serialVersionUID = 6311364761937265306L;

    public static final String D_USER = "User";

    private String username;
    private String password;
    private String email;
    private String role;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private String passwordToken;
    private String activationToken;
    private Date passwordTokenExpiration;
    private Date activationTokenExpiration;
    private int failedLogins;
    private Date lastLoginAttempt;

    public User() {
    }

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public User(String username, String password, String email, String role, boolean enabled,
                boolean accountNonLocked, boolean accountNonExpired,
                boolean credentialsNonExpired, String passwordToken, Date passwordTokenExpiration,
                String activationToken, Date activationTokenExpiration, int failedLogins, Date lastLoginAttempt) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.passwordToken = passwordToken;
        this.passwordTokenExpiration = passwordTokenExpiration;
        this.activationToken = activationToken;
        this.activationTokenExpiration = activationTokenExpiration;
        this.failedLogins = failedLogins;
        this.lastLoginAttempt = lastLoginAttempt;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public String getPasswordToken() {
        return passwordToken;
    }

    public void setPasswordToken(String passwordToken) {
        this.passwordToken = passwordToken;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public Date getPasswordTokenExpiration() {
        return passwordTokenExpiration;
    }

    public void setPasswordTokenExpiration(Date passwordTokenExpiration) {
        this.passwordTokenExpiration = passwordTokenExpiration;
    }

    public Date getActivationTokenExpiration() {
        return activationTokenExpiration;
    }

    public void setActivationTokenExpiration(Date activationTokenExpiration) {
        this.activationTokenExpiration = activationTokenExpiration;
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

    @Override
    public boolean isAccountNonExpired() {
        return this.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*Collection<GrantedAuthority> authorities = new ArrayList<>();
		
        User userRole = new User(getUsername(),getRole());
        
        if(userRole.getRole() != null) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
                authorities.add(authority);
        }
 
        */
        return null;
    }
}