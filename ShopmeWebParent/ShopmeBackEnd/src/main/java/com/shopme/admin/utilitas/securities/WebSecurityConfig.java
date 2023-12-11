package com.shopme.admin.utilitas.securities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;

import static org.springframework.security.config.Customizer.withDefaults;
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
		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.COOKIES));
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((req) -> {
                    req.anyRequest()
                            .authenticated();
                })
                
                .logout((lg)->{
                	lg.permitAll();
                	lg.addLogoutHandler(clearSiteData);
                })
                .httpBasic(withDefaults());
		return http.build();
	}
}
