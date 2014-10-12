package org.sobiech.inspigen.service;

public interface EmailService {

	public void sendTokenMail(String email, String tokenType, String token);

}