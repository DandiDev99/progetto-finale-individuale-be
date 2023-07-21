package com.davidegiannetti.service.impl;

import com.davidegiannetti.entity.User;
import com.davidegiannetti.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${email.link}")
    private String link;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendConfirmationEmail(String email,String password){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(email);
        mail.setSubject("Confirmation Email");
        mail.setText("Account generato correttamente, di seguito le credenziali per il primo accesso: " +
                "\nEmail: " + email +
                "\nPassword: " + password +
                "\nRicorda di cambiare la password per la tua sicurezza!" +
                "\nCorri a fare il tuo primo login: " + link );
        mailSender.send(mail);
    }
}
