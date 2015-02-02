package org.sobiech.inspigen.core.services;

//Interfejs zawierajacy prototypy metod wysyłających emaile
public interface IEmailService {

	public void sendTokenMail(String email, String tokenType, String token); 	//Wysyłanie emaila z linkiem zawierającym odpowiedni token

}