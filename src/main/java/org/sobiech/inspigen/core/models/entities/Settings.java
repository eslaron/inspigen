package org.sobiech.inspigen.core.models.entities;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity  
@Table(name="ig_settings")
public class Settings extends BaseEntity {

	private int MAX_ATTEMPTS = 3; //maksymalna liczba podejść
    private int LOCK_TIME = 15; //czas blokady użytkownika w minutach
    private int EXPIRATION_TIME = 2880; //czas po którym wygaśnie link lub zost(minuty)
    private int DELETION_TIME = 2; // usunięcie zasob-u/ów po N dniach.
    

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
	public int getDELETION_TIME() {
		return DELETION_TIME;
	}
	public void setDELETION_TIME(int dELETION_TIME) {
		DELETION_TIME = dELETION_TIME;
	}
}