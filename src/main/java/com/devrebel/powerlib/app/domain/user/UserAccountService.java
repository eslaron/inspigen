package com.devrebel.powerlib.app.domain.user;

import java.util.Date;

public interface UserAccountService {

    String encodePassword(User data);

    String generateToken();

    Date setTokenExpirationDate();

    Boolean checkIfTokenExpired(String tokenType, String token);

    void sendTokenMail(String email, String tokenType, String token);
}