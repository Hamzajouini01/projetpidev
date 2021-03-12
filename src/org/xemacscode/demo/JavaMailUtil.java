/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ASUS
 */
public class JavaMailUtil {
    
  public void sendMail() {
 // Add recipient
 String to = "test@example.com";

// Add sender
 String from = "myusername@gmail.com";
 final String username = "6cf727084b6801";//your Gmail username 
 final String password = "645a20c1ec1351";//your Gmail password

String host = "smtp.mailtrap.io";

Properties props = new Properties();
 props.put("mail.smtp.auth", "true");
 props.put("mail.smtp.starttls.enable", "true"); 
 props.put("mail.smtp.host", host);
 props.put("mail.smtp.port", "587");

// Get the Session object
 Session session = Session.getInstance(props,
 new javax.mail.Authenticator() {
 protected PasswordAuthentication getPasswordAuthentication() {
 return new PasswordAuthentication(username, password);
 }
 });

try {
 // Create a default MimeMessage object
 Message message = new MimeMessage(session);
 
 message.setFrom(new InternetAddress(from));
 
 message.setRecipients(Message.RecipientType.TO,
 InternetAddress.parse(to));
 
 // Set Subject
 message.setSubject("Emploi du Temps");
 
 // Put the content of your message
 message.setText("Bonjour, voici votre emploi du temps !!!");

// Send message
 Transport.send(message);

System.out.println("Sent message successfully....");

} catch (MessagingException e) {
 throw new RuntimeException(e);
 }
 }
}