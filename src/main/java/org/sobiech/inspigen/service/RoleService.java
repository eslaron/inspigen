package org.sobiech.inspigen.service;

import java.util.List;

import org.sobiech.inspigen.model.Role;
import org.sobiech.inspigen.model.User;

public interface RoleService {

	// ROLA UŻYTKOWNIKA

	public void addRole(User user);
		
	public Role getRoleById(int userId);

	public Role getRoleByName(String username);
	    
	public Role getRoleByEmail(String email);

	public void updateRole(Role role);

	public List<Role> getRoles();

}
