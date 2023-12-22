package com.shopme.admin.utilitas.securities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MySecurityUser extends User {
	

	private String fullname;
	private String email;
	private Integer id;
	private String photo;

	private MySecurityUser(Builder builder) {
		super(builder.username, builder.password, builder.authorities);
		this.fullname=builder.fullname;
		this.email=builder.email;
		this.id=builder.id;
		this.photo=builder.photo;
	}

	public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }
    
    public Integer getId() {
    	return id;
    }
    
    public String getPhoto() {
    	return photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        MySecurityUser that = (MySecurityUser) o;
        return fullname.equals(that.fullname)  && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fullname, email);
    }

    public static class Builder {

        private String fullname;
        private String email;
        private String username;
        private String password;
        private Integer id;
        private String photo;

        private Collection<? extends GrantedAuthority> authorities;


        public Builder withFullname(String fullname){
            this.fullname = fullname;
            return this;
        }

        public Builder withUsername(String username){
            this.username = username;
            return this;
        }

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }
        
        public Builder withId(Integer id) {
        	this.id= id;
        	return this;
        }
        
        public Builder withPhoto(String photo) {
        	this.photo = photo;
        	return this;
        }

        public MySecurityUser build() {
            return new MySecurityUser(this);
        }

    }

	@Override
	public String toString() {
		return "MySecurityUser  [id=" + id + ", name=" + fullname + ", email=" + email + "] " + super.toString();
	}
	
	
}
