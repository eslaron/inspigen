package org.sobiech.inspigen.dao;

import java.util.List;

import org.sobiech.inspigen.model.Role;

public interface RoleDAO {
	
	// ROLA UŻYTKOWNIKA

	public void addRole(Role role);
		
	public Role getRoleById(int userId);

	public Role getRoleByName(String username);
	    
	public Role getRoleByEmail(String email);

	public void updateRole(Role role);

	public List<Role> getRoles();
}
