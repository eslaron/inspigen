package org.sobiech.inspigen.model;
 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
 
@Entity  
@Table(name="ig_login_attempts")
public class LoginAttempts extends BaseEntity {
 
	@Column(name = "username")
	private String username;
	
	@Column(name = "attempts")
	private int attempts;
	
	@Column(name = "lastModified")
	private Date lastModified;
 
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}