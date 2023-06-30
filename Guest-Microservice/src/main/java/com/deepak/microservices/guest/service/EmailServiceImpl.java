package com.deepak.microservices.guest.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static final String SUBJECT = "Welcome to Our JW Marriott Hotels!";
    private static final String MESSAGE = "Dear Guest,\r\n" + "\r\n"
            + "Welcome to our hotel! We're thrilled to have you as part of our valued guest community.\r\n" + "\r\n"
            + "At JW Marriott Hotels, we're committed to providing exceptional hospitality and ensuring your stay is unforgettable.\r\n"
            + "\r\n"
            + "As a member, you'll enjoy exclusive benefits and personalized services. Our dedicated team is here to cater to your every need.\r\n"
            + "\r\n" + "Stay tuned for exciting updates, special offers, and promotions straight to your inbox.\r\n"
            + "\r\n"
            + "Thank you for choosing JW Marriott Hotels. We can't wait to provide you with an amazing stay.\r\n"
            + "\r\n" + "Best regards,\r\n" + "\r\n" + "JWT Greetings\r\n" + "\r\n" + "JW Marriott Hotels";
    private static final String FROM = "jwmariothotels@gmail.com";

    public boolean sendEmail(String to) {
        boolean f = false;

        // Variable for email
        String host = "smtp.gmail.com";

        // get the system properties
        Properties properties = System.getProperties();

        // Setting Important Information
        // host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Step 1: Get Session Object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jwmariothotels@gmail.com", "wbbcseycrhgywqyz");
            }
        });

        // Step 2: Compose the message
        session.setDebug(true);

        MimeMessage m = new MimeMessage(session);

        try {
            // from email
            m.setFrom(FROM);

            // adding subject to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // adding subject to message
            m.setSubject(SUBJECT);

            // adding text to message
            m.setText(MESSAGE);

            // send

            // Step 3: Send the message using Transport class
            try (Transport transport = session.getTransport()) {
                transport.connect();
                transport.sendMessage(m, m.getAllRecipients());
            }

            logger.info("Sent Success.............................");
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
