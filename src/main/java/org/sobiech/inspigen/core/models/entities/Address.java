package org.sobiech.inspigen.core.models.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity  
@Table(name="ig_addresses")
public class Address extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7921700639539462190L;

	private String address;
	
	private String city;
	
	private String zipCode;
	
	private String state;
	
	private String country;
	
	private Boolean registeredAddress;
	
	private Boolean mailAddress;
	
	public Address() {}
	
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
}

