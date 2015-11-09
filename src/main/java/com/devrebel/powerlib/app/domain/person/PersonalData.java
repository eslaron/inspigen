package com.devrebel.powerlib.app.domain.person;

import com.devrebel.powerlib.app.domain.user.User;
import com.devrebel.powerlib.core.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="ig_personal_data")
public class PersonalData extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8562484446335390991L;
 
    private String firstName;
    private String lastName;
    private String pesel;
	private String phone;

	@OneToOne
	@PrimaryKeyJoinColumn
	User user;

	public PersonalData() {}

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}