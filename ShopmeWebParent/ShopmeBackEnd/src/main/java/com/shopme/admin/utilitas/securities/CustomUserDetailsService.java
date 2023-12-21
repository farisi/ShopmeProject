package com.shopme.admin.utilitas.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopme.admin.user.UserRepository;
import com.shopme.common.entities.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + username));
		Collection<? extends GrantedAuthority> getAuthorities = user.getRoles()
				.stream()
				.map(role->new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toList());
		if(user.isEnabled()) {
			System.out.println("user sudah di enabled");
		}
		else {
			System.out.println(" User belum di enabled ");
		}
		//return new CustomUserDetail(user.getEmail(), user.getPassword(), user.isEnabled(), getAuthorities);
		MySecurityUser usrDetail = new MySecurityUser.Builder()
				.withEmail(user.getEmail())
				.withUsername(user.getEmail())
				.withFullname(user.getFirstName() + " " +  user.getLastName())
				.withPassword(user.getPassword())
				.withAuthorities(getAuthorities)
				.build();
		return usrDetail;
	}

}
