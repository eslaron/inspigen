package org.sobiech.inspigen.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    
	@NotNull(message = "{error.user.username.null}")
    @NotEmpty(message = "{error.user.username.empty}")
    @Size(max = 50, message = "{error.user.username.max}")
    @Column(name = "username", length = 50)
    private String username;

    @NotNull(message = "{error.user.password.null}")
    @NotEmpty(message = "{error.user.password.empty}")
    @Size(max = 50, message = "{error.user.password.max}")
    @Column(name = "password", length = 50)
    private String password;
    
    @Column(name = "enabled")
    private Boolean enabled;
    
    @Column(name = "accountNonLocked", columnDefinition = "TINYINT(1)")
    private Boolean accountNonLocked;
    
	@Column(name = "email")
    private String email;
    
	@Column(name = "passwordToken")
	private String passwordToken;
	
	@Column(name = "tokenExpiration")
	private Date tokenExpiration;
	
	/*@OneToOne  
    @JoinTable(name = "ig_user_roles",  
        joinColumns        = {@JoinColumn(name = "user_id", referencedColumnName = "id")},  
        inverseJoinColumns = {@JoinColumn(name = "role_id",  referencedColumnName = "id")}  
    ) */
	

	@Transient
    private Role role;
    
	public User() {}

    public User(String username, String password, String email, boolean enabled) {
    	
    	this.username = username;
    	this.password = password;
    	this.email =  email;
    	this.enabled = enabled;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setAccountNonLocked(Boolean accountNonLocked) {
  		this.accountNonLocked = accountNonLocked;
  	}
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
	public Date getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(Date tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

	public String getPasswordToken() {
		return passwordToken;
	}

	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}
	
	//Czy konto nie wygas�o
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

    //Czy has�o nie wygas�o
    @Override
    public boolean isCredentialsNonExpired() {
        //return true = password is valid / not expired
        return true;
    }

    //Czy konto jest w��czone
    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }

    //Pobierz role
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
        Role userRoles = this.getRole();
        
        if(userRoles != null) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoles.getRolename());
                authorities.add(authority);
        }
 
    	return authorities;
	}	
}