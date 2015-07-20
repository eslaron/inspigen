package com.devrebel.inspigen.app.domain.event;

import com.devrebel.inspigen.core.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity  																//oznaczenie klasy jako encji
@Table(name="ig_events")												//nazwa tabeli w bazie danych	
public class Event extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = -508277826502034614L;

	private static final String D_EVENT = "ig_events";
	
	private String name;				//nazwa wydarzenia
						
	private String type;				//typ wydarzenia
	
	private Date startDate;				//data rozpoczęcia
	
	private Date endDate;				//data zakończenia
	
	private String description;			//opis

    private Long user_id;				//id koordynatora wydarzenia

	
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}

