package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  																//oznaczenie klasy jako encji
@Table(name="ig_system_settings")										//nazwa tabeli w bazie danych
public class Settings extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6053786706744243022L;

	private int maxLoginAttempts; 			//maksymalna liczba podejść
	
	private int accountLockTime; 			//czas blokady użytkownika w minutach
	
    private int linkExpirationTime; 		//czas po którym wygaśnie link lub zost(minuty)
    
    private String emailAddress; 			//adres z którego będą wysyłane emaile systemowe
    
    private String emailHost; 				//host z którego będą wysyłane emaile
    
    private int emailPort; 					//port z którego będą wysyłane emaile
    
    private String emailUsername; 			//nazwa użytkownika skrzynki emailowej
    
    private String emailPassword; 			//hasło do skrzynki emailowej;
    
    public Settings() {}
    
    //Gettery i settery
    
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