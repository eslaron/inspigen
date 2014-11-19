package org.sobiech.inspigen.core.services.util;

import org.sobiech.inspigen.core.models.entities.User;
import java.util.List;

public class UsersList {
	
	private List<User> users;
	
	public UsersList(List<User> users) {
		this.users = users;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setAccounts(List<User> users) {
		this.users = users;
	}
}