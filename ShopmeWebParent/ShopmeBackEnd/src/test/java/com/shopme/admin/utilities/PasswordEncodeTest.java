package com.shopme.admin.utilities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeTest {
	
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword="p455w0rd";
		String econdedPassword = passwordEncoder.encode(rawPassword);
		boolean matches = passwordEncoder.matches(rawPassword, econdedPassword);
		assertThat(matches).isTrue();
	}
}
