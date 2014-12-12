package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity  
@Table(name="ig_settings")
public class Settings extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6053786706744243022L;

	@Column(name = "maxLoginAttempts")
	private int maxLoginAttempts; //maksymalna liczba podejść
	
	@Column(name = "accountLockTime")
	private int accountLockTime; //czas blokady użytkownika w minutach
	
	@Column(name = "linkExpirationTime")
    private int linkExpirationTime; //czas po którym wygaśnie link lub zost(minuty)
	
	@Column(name = "inactiveAccountsDeletionTime")
    private int inactiveAccountsDeletionTime; // usunięcie zasob-u/ów po N dniach.
    
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
	public int getInactiveAccountsDeletionTime() {
		return inactiveAccountsDeletionTime;
	}
	public void setInactiveAccountsDeletionTime(int inactiveAccountsDeletionTime) {
		this.inactiveAccountsDeletionTime = inactiveAccountsDeletionTime;
	}
}