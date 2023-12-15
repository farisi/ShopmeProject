package com.shopme.admin.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.repositories.PasswordResetRepository;
import com.shopme.admin.user.EmailService;
import com.shopme.admin.utilitas.UrlUtils;
import com.shopme.common.entities.PasswordReset;

import jakarta.mail.MessagingException;

@Service
public class PasswordResetSrvImpl implements PasswordResetService {
	
	@Autowired
	PasswordResetRepository prRepo;
	
	@Autowired
	EmailService emailSrv;
	
	@Autowired
	UrlUtils urlSrv;

	@Override
	public PasswordReset findById(String email) {
		// TODO Auto-generated method stub
		return prRepo.findById(email).get();
	}

	@Override
	public PasswordReset save(PasswordReset rp) {
		// TODO Auto-generated method stub
		PasswordReset pr = prRepo.save(rp);
		try {
			String appaddress = urlSrv.getBaseUrl() + "/auth/reset_password/" + pr.getToken();
			System.out.println(appaddress);
			emailSrv.sendConfirmationEmail(rp.getEmail(), appaddress);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pr;
	}

	@Override
	public Optional<PasswordReset> findByToken(UUID token) {
		// TODO Auto-generated method stub
		return prRepo.findByToken(token);
	}

}
