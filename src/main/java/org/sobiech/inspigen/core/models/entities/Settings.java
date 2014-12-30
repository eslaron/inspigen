package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  
@Table(name="ig_system_settings")
public class Settings extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6053786706744243022L;

	private int maxLoginAttempts; //maksymalna liczba podejść
	
	private int accountLockTime; //czas blokady użytkownika w minutach
	
    private int linkExpirationTime; //czas po którym wygaśnie link lub zost(minuty)
    
    private int authTokenExpirationTime; //czas po którym wygaśnie token identyfikacyjny w ciasteczku
    
    private int authCookieExpirationTime; //czas po którym wygaśnie ciasteczko autentykacyjne
    
    private String emailAddress; //adres z którego będą wysyłane emaile systemowe
    
    private String emailHost; //host z którego będą wysyłane emaile
    
    private String emailPort; //port z którego będą wysyłane emaile
    
    private String emailUsername; //nazwa użytkownika skrzynki emailowej
    
    private String emailPassword; //hasło do skrzynki emailowej;
    
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

	public int getAuthTokenExpirationTime() {
		return authTokenExpirationTime;
	}

	public void setAuthTokenExpirationTime(int authTokenExpirationTime) {
		this.authTokenExpirationTime = authTokenExpirationTime;
	}

	public int getAuthCookieExpirationTime() {
		return authCookieExpirationTime;
	}

	public void setAuthCookieExpirationTime(int authCookieExpirationTime) {
		this.authCookieExpirationTime = authCookieExpirationTime;
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

	public String getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(String emailPort) {
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