package com.melaniepiotrowski.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*-------------------------------
 | Mailer class handles message |
 |     construction and sending |
 -------------------------------*/

public class Mailer {

    MimeMessage msg;
    Properties props;
    Session session;

    static final String FROM = "contact@melaniepiotrowski.com";
    static final String FROMNAME = "Website Contact Form";
    static final String TO = "piotrowme@gmail.com";
    static final String SMTP_USERNAME = "AKIAUOCKUZVZP3AXOZNR";
    static final String SMTP_PASSWORD = "BEEgchn26N8tUsFCW8EnUeIoquNSBM8RGnjNVZkeDCr9";
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    static final int PORT = 587;

    // set fallback messages for error identification
    static String subject = "Website contact form is broken (auto-generated)";    
    static String body = "Check AmazonSESSample.java for text assignment errors.";

    // set protocol & port
    public void setMailServerProperties() {
        props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
    }

    // create session and build message
    public void createEmailMessage(String msgSender, String msgSenderEmail, String emailSubject, String emailBody) throws AddressException, IOException, MessagingException {
        session = Session.getDefaultInstance(props);

        msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));

        subject = emailSubject;
        body = String.join(
            System.getProperty("line.separator"),
            "<h1>Message sent from MelaniePiotrowski.com Contact Form</h1>",
            "From: " + msgSender + ", Email: " + msgSenderEmail + "<br>", 
            "Message: '" + emailBody + "'");

        msg.setSubject(subject);
        msg.setContent(body,"text/html");
    }

    // send message and close connection
    public void sendEmail() throws AddressException, MessagingException {
        Transport transport = session.getTransport();
        transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }
}