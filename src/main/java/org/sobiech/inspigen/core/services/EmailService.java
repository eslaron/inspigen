package org.sobiech.inspigen.core.services;

public interface EmailService {

	public void sendTokenMail(String email, String tokenType, String token);

}