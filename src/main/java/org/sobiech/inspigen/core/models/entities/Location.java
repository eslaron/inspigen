package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  
@Table(name="ig_locations")
public class Location extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3879277972207055944L;
	
	private String name;
	
	private String type;

	private String adress;
	
	private String city;
	
	private String zipCode;
	
	private String state;
	
	private String country;
	
	private String phoneNumber;
	
	private String email;	
	
	public Location() {}
	
	
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
	
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

