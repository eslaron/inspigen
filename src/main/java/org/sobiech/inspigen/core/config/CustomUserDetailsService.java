package org.sobiech.inspigen.core.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
 


import javax.annotation.PostConstruct;
import javax.sql.DataSource;
 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

//Klasa zawierająca nadpisane metody wykorzystywane przy identyfikacji użytkownika
public class CustomUserDetailsService extends JdbcDaoImpl implements UserDetailsService {
 
	@Autowired
	private DataSource dataSource;
	
	@Bean
	User getUser(String username, 
			String password, 
			boolean enabled, 
			boolean accountNonExpired, 
			boolean credentialsNonExpired, 
			boolean accountNonLocked, 
			Collection<? extends GrantedAuthority> authorities) {
		
		User user = new User(username, 
				password, 
				enabled, 
				accountNonExpired, 
				credentialsNonExpired, 
				accountNonLocked,
				authorities);
	
		return user;
	}
 
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
 
	@Override
	@Value("select * from ig_users where username = ?")
	public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
		super.setUsersByUsernameQuery(usersByUsernameQueryString);
	}
	
	@Override
	@Value("select username, role from ig_users where username =?")
	public void setAuthoritiesByUsernameQuery(String queryString) {
		super.setAuthoritiesByUsernameQuery(queryString);
	}
 
	/* Nadpisana metoda pozwalająca na pobranie informacji o użytkowniku, w tym informacji o tym
	czy jest zablokowany */
	@Override
	public List<UserDetails> loadUsersByUsername(String username) {
	  return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[] { username },
		new RowMapper<UserDetails>() {
		  public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("username");
			String password = rs.getString("password");
			boolean enabled = rs.getBoolean("enabled");
			boolean accountNonExpired = rs.getBoolean("accountNonExpired");
			boolean credentialsNonExpired = rs.getBoolean("credentialsNonExpired");
			boolean accountNonLocked = rs.getBoolean("accountNonLocked");
 
			return getUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
		  }
 
	  });
	}
 
	//Metoda nadpisana w celu przekazania wartości statusow użytkownika, w tym blokady użytkownika
	@Override
	public UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();
 
		if (super.isUsernameBasedPrimaryKey()) {
		  returnUsername = username;
		}
 
		return getUser(returnUsername, userFromUserQuery.getPassword(), 
                       userFromUserQuery.isEnabled(),
		       userFromUserQuery.isAccountNonExpired(), 
                       userFromUserQuery.isCredentialsNonExpired(),
			userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
	}
}