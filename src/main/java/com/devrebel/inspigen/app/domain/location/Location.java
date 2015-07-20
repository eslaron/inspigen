package com.devrebel.inspigen.app.domain.location;

import com.devrebel.inspigen.core.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity 																	//oznaczenie klasy jako encji
@Table(name="ig_locations")											//nazwa tabeli w bazie danych
public class Location extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 3879277972207055944L;
	
	private String name;				//nazwa lokacji
	
	private String type;				//typ lokacji

	private String address;				//adres lokacji (ulica itp. itd.)
	
	private String city;				//miasto
	
	private String zipCode;				//kod pocztowy
	
	private String state;				//wojewodztwo
	
	private String country;				//kraj

	private String phone;			//numer telefonu(opcjonalny)
	
	private String email;				//email kontaktowy(opcjonalny)
	
	public Location() {}
	
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
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

