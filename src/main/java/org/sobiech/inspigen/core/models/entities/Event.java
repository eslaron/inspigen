package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  																//oznaczenie klasy jako encji
@Table(name="ig_events")												//nazwa tabeli w bazie danych	
public class Event extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -508277826502034614L;
	
	private String name;				//nazwa wydarzenia
						
	private String type;				//typ wydarzenia
	
	private Date startDate;				//data rozpoczęcia
	
	private Date endDate;				//data zakończenia
	
	private String description;			//opis
	
	private int location_id;			//id miejsca, w ktorym ma się odbyć wydarzenie
	
	private int user_id;				//id koordynatora wydarzenia
	
	public Event() {}
	
	//Gettery i settery
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLocation_id() {
		return location_id;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}

