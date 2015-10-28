package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.common.AbstractEntity;
import org.dozer.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "ig_users")
public class User extends AbstractEntity implements UserDetails {

    private static final long serialVersionUID = 6311364761937265306L;
    public static final String D_USER = "User";

    @Column(unique = true)
    @Mapping("username")
    private String username;

    @Mapping("password")
    private String password;

    @Column(unique = true)
    @Mapping("email")
    private String email;

    @Mapping("role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Mapping("enabled")
    private  Boolean enabled;
    private Boolean accountNonLocked;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private String passwordToken;
    private String activationToken;
    private Date passwordTokenExpiration;
    private Date activationTokenExpiration;
    private int failedLogins;
    private Date lastLoginAttempt;
    private Date lastActive;

    private User() {}

    private User(UserBuilder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.role = builder.role;
        this.enabled = builder.enabled;
        this.accountNonLocked = builder.accountNonLocked;
        this.accountNonExpired = builder.accountNonExpired;
        this.credentialsNonExpired = builder.credentialsNonExpired;
        this.passwordToken = builder.passwordToken;
        this.passwordTokenExpiration = builder.passwordTokenExpiration;
        this.activationToken = builder.activationToken;
        this.activationTokenExpiration = builder.activationTokenExpiration;
        this.failedLogins = builder.failedLogins;
        this.lastLoginAttempt = builder.lastLoginAttempt;
        this.lastActive = builder.lastActive;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public String getPasswordToken() {
        return passwordToken;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public Date getPasswordTokenExpiration() {
        return passwordTokenExpiration;
    }

    public Date getActivationTokenExpiration() {
        return activationTokenExpiration;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public Date getLastLoginAttempt() {
        return lastLoginAttempt;
    }

    public Date getLastActive() {
        return lastActive;
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

    public static class UserBuilder {

        private Long id;
        private String username;
        private String password;
        private String email;
        private UserRole role;
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
        private Date lastActive;

        public UserBuilder(String username, UserRole role) {
            this.username = username;
            this.role = role;
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder accountNonLocked(Boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public UserBuilder accountNonExpired(Boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public UserBuilder credentialsNonExpired(Boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public UserBuilder passwordToken(String passwordToken) {
            this.passwordToken = passwordToken;
            return this;
        }

        public UserBuilder activationToken(String activationToken) {
            this.activationToken = activationToken;
            return this;
        }

        public UserBuilder passwordTokenExpiration(Date passwordTokenExpiration) {
            this.passwordTokenExpiration = passwordTokenExpiration;
            return this;
        }

        public UserBuilder activationTokenExpiration(Date activationTokenExpiration) {
            this.activationTokenExpiration = activationTokenExpiration;
            return this;
        }

        public UserBuilder failedLogins(int failedLogins) {
            this.failedLogins = failedLogins;
            return this;
        }

        public UserBuilder lastLoginAttempt(Date lastLoginAttempt) {
            this.lastLoginAttempt = lastLoginAttempt;
            return this;
        }

        public UserBuilder lastActive(Date lastActive) {
            this.lastActive = lastActive;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public UserBuilder toBuilder() {
        return new UserBuilder(getUsername(), getRole())
                .id(getId())
                .password(getPassword())
                .email(getEmail())
                .enabled(getEnabled())
                .accountNonLocked(getAccountNonLocked())
                .accountNonExpired(getAccountNonExpired())
                .credentialsNonExpired(getCredentialsNonExpired())
                .activationToken(getActivationToken())
                .passwordToken(getPasswordToken())
                .passwordTokenExpiration(getPasswordTokenExpiration())
                .activationTokenExpiration(getActivationTokenExpiration())
                .failedLogins(getFailedLogins())
                .lastLoginAttempt(getLastLoginAttempt())
                .lastActive(getLastActive());
    }
}