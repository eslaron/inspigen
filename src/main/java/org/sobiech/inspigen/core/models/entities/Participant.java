package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity  
@Table(name="ig_participants")
public class Participant extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3146080687613852144L;
	
	private int event_id;
	
	private int user_id;
	
	private String eventRole;
	
	public Participant() {}
	
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

	public String getEventRole() {
		return eventRole;
	}

	public void setEventRole(String eventRole) {
		this.eventRole = eventRole;
	}
}