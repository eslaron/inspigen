package org.sobiech.inspigen.core.services;

public interface IEmailService {

	public void sendTokenMail(String email, String tokenType, String token);

}