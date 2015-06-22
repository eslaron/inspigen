package org.sobiech.inspigen.core.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity  																	//oznaczenie klasy jako encji
@Table(name="ig_event_participants")										//nazwa tabeli w bazie danych
public class Participant extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3146080687613852144L;
	
	private int event_id;			//id wydarzenia, w ktorym ma uczestniczyć członek organizacji
	
	private int user_id;			//id uczestnika
	
	private Boolean approved;		//status akceptacji uczestnictwa w wydarzeniu
	
	private String eventRole;		//rola pełniona w wydarzeniu
	
	public Participant() {}
	
	//Gettery i settery
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public String getEventRole() {
		return eventRole;
	}

	public void setEventRole(String eventRole) {
		this.eventRole = eventRole;
	}
}