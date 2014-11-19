package org.sobiech.inspigen.core.models.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity  
@Table(name="ig_users")
public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 6311364761937265306L;
    static Logger logger = LoggerFactory.getLogger(User.class);
        
    @NotEmpty
    @Size(min = 5, max = 12, message="SizeValidationFailed")
    @Pattern(regexp = "^[A-z][A-z0-9]*$", message = "PatternValidationFailed")
    @Column(name = "username", unique=true)
    private String username;

    @NotEmpty
    @Column(name = "password")
    private String password;
    
    @Email(message="emailValidationFailed")
    @Size(min = 6, max = 40)
    @Column(name = "email", unique=true)
    private String email;
    
    @NotEmpty
    @Column(name="role")
    private String role;
    
    @Column(name = "enabled", columnDefinition = "TINYINT(1)")
    private Boolean enabled;
    
	@Column(name = "accountNonLocked", columnDefinition = "TINYINT(1)")
    private Boolean accountNonLocked;
    
    @AssertTrue
    @Column(name = "accountNonExpired", columnDefinition = "TINYINT(1)")
    private Boolean accountNonExpired;
    
    @AssertTrue
    @Column(name = "credentialsNonExpired", columnDefinition = "TINYINT(1)")
    private Boolean credentialsNonExpired;
    
    @NotNull
	@Column(name = "passwordToken")
	private String passwordToken;
	
    @NotNull
	@Column(name = "activationToken")
	private String activationToken;
	
	@Column(name = "passwordTokenExpiration")
	private Date passwordTokenExpiration;
	
	@Column(name = "activationTokenExpiration")
	private Date activationTokenExpiration;
	
	@Column(name = "failedLogins")
	private int failedLogins;
	
	@Column(name = "lastLoginAttempt")
	private Timestamp lastLoginAttempt;
		  
	public User() {}

	public User(String activationToken) {
	  	
		this.activationToken = activationToken;
	}
	
	public User(String activationToken, boolean enabled) {
		
		this.activationToken = activationToken;
		this.enabled = enabled;
	}
	
	public User(String username, String role) {
	  	
		this.username = username;
		this.role = role;
	}
	
	public User(String username, String password, String email) {
  	
		this.username = username;
		this.password = password;
		this.email =  email;
	}
	
    public User(String username, String password, String email, String role, boolean enabled, 
    			boolean accountNonLocked, boolean accountNonExpired, 
    			boolean credentialsNonExpired, String passwordToken, Date passwordTokenExpiration,
    			String activationToken, Date activationTokenExpiration, int failedLogins) {
    	
    	this.username = username;
    	this.password = password;
    	this.email =  email;
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
    
    public void setAccountNonLocked(Boolean accountNonLocked) {
  		this.accountNonLocked = accountNonLocked;
  	}
 
	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
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

	public Timestamp getLastLoginAttempt() {
		return lastLoginAttempt;
	}

	public void setLastLoginAttempt(Timestamp lastLoginAttempt) {
		this.lastLoginAttempt = lastLoginAttempt;
	}
    
	//Czy konto nie wygasło
    @Override
    public boolean isAccountNonExpired() {
        //return true = account is valid / not expired
        return true; 
    }

    //Czy konto jest odblokowane
    @Override
    public boolean isAccountNonLocked() {
        //return true = account is not locked
        return true;
    }

    //Czy has�o nie wygassło
    @Override
    public boolean isCredentialsNonExpired() {
        //return true = password is valid / not expired
        return true;
    }

    //Czy konto jest włączone
    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }

    //Pobierz role
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
        User userRole = new User(getUsername(),getRole());
        
        if(userRole.getRole() != null) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
                authorities.add(authority);
        }
 
        System.out.println(authorities);
    	return authorities;
	}	
}