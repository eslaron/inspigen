package org.sobiech.inspigen.core.models.DTO;

import java.util.Date;

public class UserDTO {
	
	private Long id;
	
	private String username;
	
	private String password;
    
	private String email;
    
    private String role;
    
    private Boolean enabled;
    
    private Boolean locked;
	
	private int failedLogins;
	
	private Date lastLoginAttempt;
	

	public UserDTO() {}
	
	
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
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