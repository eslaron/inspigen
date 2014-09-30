package org.sobiech.inspigen.model;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity  
@Table(name="ig_settings")
public class Settings extends BaseEntity {

	private int MAX_ATTEMPTS = 3; //maksymalna liczba podejœæ
    private int LOCK_TIME = 15; //czas blokady u¿ytkownika w minutach
    private int EXPIRATION_TIME = 720; //czas po którym wygaœnie link (minuty)
    
	public int getMAX_ATTEMPTS() {
		return MAX_ATTEMPTS;
	}
	public void setMAX_ATTEMPTS(int mAX_ATTEMPTS) {
		MAX_ATTEMPTS = mAX_ATTEMPTS;
	}
	public int getLOCK_TIME() {
		return LOCK_TIME;
	}
	public void setLOCK_TIME(int lOCK_TIME) {
		LOCK_TIME = lOCK_TIME;
	} 	
    public int getEXPIRATION_TIME() {
		return EXPIRATION_TIME;
	}
	public void setEXPIRATION_TIME(int eXPIRATION_TIME) {
		EXPIRATION_TIME = eXPIRATION_TIME;
	}
}