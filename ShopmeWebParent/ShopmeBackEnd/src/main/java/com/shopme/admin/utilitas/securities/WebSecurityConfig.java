package com.shopme.admin.utilitas.securities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@Bean
    public PasswordEncoder  passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((req) -> {
                	req.requestMatchers(HttpMethod.GET, "/auth/reset_password")
                		.permitAll();
                	req.requestMatchers(HttpMethod.POST, "/auth/reset_password")
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
