package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  															//oznaczenie klasy jako encji
@Table(name="ig_addresses") 										//nazwa tabeli w bazie danych
public class Address extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7921700639539462190L;

	private String address;					//ulica, numer domu, numer lokalu
	
	private String city;					//miasto
	
	private String zipCode;					//kod pocztowy
	
	private String state;					//wojewodztwo
	
	private String country;					//kraj
	
	private Boolean registeredAddress;		//adres zameldowania
	
	private Boolean mailAddress;			//adres korespondencyjny
	
	private int user_id;					//id użytkownika do ktorego należy adres
	
	public Address() {}
	
	//Gettery i settery
	
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

	public Boolean getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(Boolean registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public Boolean getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(Boolean mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}

