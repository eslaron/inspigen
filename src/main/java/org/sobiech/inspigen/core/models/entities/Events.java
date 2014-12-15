package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import java.util.Date;


public class Events extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -508277826502034614L;
	
	private String type;
	
	private Date startDate;
	
	private Date endDate;
	
	private String description;
	
	private int user_id;
	
	public Events() {}
	
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
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}

