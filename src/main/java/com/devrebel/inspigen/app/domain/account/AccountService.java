package com.devrebel.inspigen.app.domain.account;

public interface AccountService {

	void sendTokenMail(String email, String tokenType, String token);
}