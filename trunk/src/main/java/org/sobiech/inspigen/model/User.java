package org.sobiech.inspigen.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    private boolean enabled;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "lastName")
    private String lastName;
 
    @Column(name = "pesel")
    private String pesel;
    
	@Column(name = "phoneNumber")
    private String phoneNumber;
    
	
	@OneToOne  
    @JoinTable(name = "ig_user_roles",  
        joinColumns        = {@JoinColumn(name = "user_id", referencedColumnName = "id")},  
        inverseJoinColumns = {@JoinColumn(name = "role_id",  referencedColumnName = "id")}  
    )  
	
    private Role role;
    
	public User() {}

    public User(String username, String password, String email, String firstName, String lastName, 
    			String pesel, String phoneNumber, Boolean enabled) {
    	
    	this.username = username;
    	this.password = password;
    	this.email =  email;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.pesel = pesel;
    	this.phoneNumber = phoneNumber;
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

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
    
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
    public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	//Czy konto nie wygas³o
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

    //Czy has³o nie wygas³o
    @Override
    public boolean isCredentialsNonExpired() {
        //return true = password is valid / not expired
        return true;
    }

    //Czy konto jest w³¹czone
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