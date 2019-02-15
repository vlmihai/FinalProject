package finalprojectNew.security;

import finalprojectNew.business.entities.AppUser;
import finalprojectNew.business.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		AppUser appUser;
		try {
			appUser = appUserRepository.findByUsernameIgnoreCase(userId);
			if (appUser == null)
				throw new UsernameNotFoundException("username not found");

		} catch (Exception e) {
			throw new UsernameNotFoundException("database error");
		}
		return buildUserFromUserEntity (appUser);
	}

	private UserDetails buildUserFromUserEntity (AppUser appUserEntity){
		String username = appUserEntity.getUsername();
		String password = appUserEntity.getPassword();
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		SimpleGrantedAuthority[] authorities = new SimpleGrantedAuthority[1];
		authorities[0] = new SimpleGrantedAuthority(appUserEntity.getRole());
		return new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, Arrays.asList(authorities));
	}
}