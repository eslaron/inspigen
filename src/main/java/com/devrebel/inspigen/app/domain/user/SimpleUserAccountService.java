package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.web.exception.message.MessageDTO;
import com.devrebel.inspigen.core.web.exception.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@Service
@Transactional
public class SimpleUserAccountService implements UserAccountService {

    private static final String EMAIL_NOT_FOUND_ERROR_CODE = "error.user.account.email.not.found";
    private static final String PASSWORD_CHANGED_SUCCESS_CODE = "success.user.account.password.changed";

    private static final String RESET_LINK_INVALID_ERROR_CODE = "error.user.account.reset.link.invalid";
    private static final String RESET_LINK_EXPIRED_ERROR_CODE = "error.user.account.reset.link.expired";
    private static final String RESET_LINK_SENT_SUCCESS_CODE = "success.user.account.reset.link.sent";

    private static final String ACCOUNT_ACTIVATED_SUCCESS_CODE = "success.user.account.activated";
    private static final String ACCOUNT_ALREADY_ACTIVATED_INFO_CODE = "info.user.account.already.activated";

    private static final String ACTIVATION_LINK_INVALID_ERROR_CODE = "error.user.account.invalid.activation.link";
    private static final String ACTIVATION_LINK_EXPIRED_INFO_CODE = "error.user.account.activation.link.expired";

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    Md5PasswordEncoder passwordEncoder;

    @Autowired
    ReflectionSaltSource saltSource;

    @Autowired
    MessageSource messageSource;

    MessageDTO message;

    @Override
    public String encodePassword(User data) {
        String password = data.getPassword();
        String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(data));

        return encodedPassword;
    }

    @Override
    public String generateToken() {
        char[] VALID_CHARACTERS =
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879".toCharArray();
        SecureRandom secureRandom = new SecureRandom();
        Random random = new Random();
        char[] buff = new char[16];
        for (int i = 0; i < 16; ++i) {
            if ((i % 10) == 0) {
                random.setSeed(secureRandom.nextLong());
            }
            buff[i] = VALID_CHARACTERS[random.nextInt(VALID_CHARACTERS.length)];
        }
        return String.valueOf(buff);
    }

    @Override
    public Date setTokenExpirationDate() {
        Date currentDate = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.format(currentDate);

        Calendar cal = Calendar.getInstance();
        cal = format.getCalendar();
        cal.add(Calendar.MINUTE, 2280);

        return (Date) cal.getTime();
    }

    @Override
    public Boolean checkIfTokenExpired(String tokenType, String token) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User userByToken = new User();

        if (tokenType == "activationToken") {
            userByToken = userRepository.findByActivationToken(token);
            format.format(userByToken.getActivationTokenExpiration());
        }

        if (tokenType == "passwordToken") {
            userByToken = userRepository.findByPasswordToken(token);
            format.format(userByToken.getPasswordTokenExpiration());
        }

        Calendar expire = Calendar.getInstance();
        expire = format.getCalendar();

        if (Calendar.getInstance().getTime().after(expire.getTime()) == true) {
            return true;
        } else return false;
    }

    @Override
    public void sendTokenMail(String email, String tokenType, String token) {
        /*String msg = "";
        Multipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeHelper;

        try {
            mimeHelper = new MimeMessageHelper(message, true);
            mimeHelper.setTo(email);
            mimeHelper.setFrom("system.inspigen@gmail.com");

            if (tokenType == "activationToken") {
                mimeHelper.setSubject("Witamy w systemie Inspigen!");

                msg = "<html><body>Hej :)<br/>Cieszymy się, że do nas dołączyłeś."
                        + "<br/>Kliknij w podany link, aby aktywować swoje konto: "
                        + "<a href='http://localhost:8080/inspigen/#/activateAccount/"
                        + token + "'>LINK</a></body></html>";

                mbp.setContent(msg, "text/html; charset=UTF-8");
                mp.addBodyPart(mbp);
                message.setContent(mp);
            }

            if (tokenType == "passwordToken") {
                mimeHelper.setSubject("Reset hasła - Inspigen");

                msg = "<html><body>Hej :)<br/>Aby zresetować swoje hasło kliknij w poniższy link: <br/>"
                        + "<a href='http://localhost:8080/inspigen/#/newPassword/"
                        + token + "'>LINK</a></body></html>";

                mbp.setContent(msg, "text/html; charset=UTF-8");
                mp.addBodyPart(mbp);
                message.setContent(mp);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("Error Sending email " + e.getMessage());
        }*/
    }
}