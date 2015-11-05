package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.app.domain.user.User;

import java.util.Date;

public interface UserAccountService {

    String encodePassword(User data);

    String generateToken();

    Date setTokenExpirationDate();

    Boolean checkIfTokenExpired(String tokenType, String token);

    void sendTokenMail(String email, String tokenType, String token);
}