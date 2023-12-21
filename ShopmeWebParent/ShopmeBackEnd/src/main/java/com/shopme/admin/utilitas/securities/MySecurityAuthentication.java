package com.shopme.admin.utilitas.securities;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MySecurityAuthentication implements Authentication {
	
private static final long serialVersionUID = 1L;
	
	private final boolean isAuthenticated;
	private final String name;
	private final String password;
	private final MySecurityUser mySecurityUser;
	private final Collection<GrantedAuthority> authorities;
	
	private MySecurityAuthentication(Collection<GrantedAuthority> authorities, String name, MySecurityUser mySecurityUser, String password) {
		this.authorities = authorities;
		this.name = name;
		this.password = password;
		this.mySecurityUser = mySecurityUser;
		this.isAuthenticated = password == null;
	}
	
	public static MySecurityAuthentication unauthenticated(String name, String password) {
		return new MySecurityAuthentication(Collections.emptyList(), name, null, password);
	}
	
	public static MySecurityAuthentication authenticated(MySecurityUser myUser) {
		return new MySecurityAuthentication(myUser.getAuthorities(), myUser.getUsername(), myUser, null);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return mySecurityUser;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("Don't do this");
	}
	
	public String getPassword() {
		return password;
	}


}
