package org.sobiech.inspigen.dao;

import java.util.List;

import org.sobiech.inspigen.model.Role;

public interface RoleDAO {
	
	// ROLA UŻYTKOWNIKA

	public void addUserRole(Role role);
		
	public Role getUserRoleById(int userId);

	public Role getUserRoleByName(String username);
	    
	public Role getUserRoleByEmail(String email);

	public void updateUserRole(Role role);

	public List<Role> getRoles();
}
