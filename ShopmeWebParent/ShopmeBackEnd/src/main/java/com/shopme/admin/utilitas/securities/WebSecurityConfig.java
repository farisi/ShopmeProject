package com.shopme.admin.utilitas.securities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.annotation.Resource;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//	@Resource
//	UserDetailsService usrDetail;
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	@Bean
	UserDetailsService myUserDetailsService() {
		return new CustomUserDetailsService();
	}

	
	@Bean
    DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authProvider());
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((req) -> {
                	req.requestMatchers("/error/**").permitAll();
                	req.requestMatchers(HttpMethod.GET,"/register").permitAll();
                	req.requestMatchers(HttpMethod.POST,"/register").permitAll();
                	req.requestMatchers(HttpMethod.GET, "/auth/reset_password*")
                		.permitAll();
                	req.requestMatchers(HttpMethod.GET, "/auth/reset_password/**")
            		.permitAll();
                	req.requestMatchers("/auth/reset_password/**")
                		.permitAll();
                    req.anyRequest()
                        .authenticated();
                })
                .formLogin(login->login.loginPage("/login").permitAll())
               .logout(logout -> {
                   logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                   logout.logoutSuccessUrl("/");
                   logout.deleteCookies("JSESSIONID");
                   logout.invalidateHttpSession(true);
               });
               //http.exceptionHandling((e)->e.accessDeniedPage("/403"));

		return http.build();
	}
}
