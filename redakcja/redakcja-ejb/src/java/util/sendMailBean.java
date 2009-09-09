/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author arekp
 */
@Stateless
public class sendMailBean implements sendMailLocal {

    public void sendMsg(String email,String cc, String subject, String body, String filename) {
        System.out.print("mail --> " + email + " " + subject + " " + body);
//		Properties props = new Properties();
        InitialContext ictx;
        javax.mail.Session mailSession = null;
        try {
            ictx = new InitialContext();
            mailSession = (javax.mail.Session) ictx.lookup("mail/MailTest");
        } catch (NamingException ex) {
            System.out.print("blad pobierania JNDI dla mail");
            Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, null, ex);
        }


//		Session mailSessoin = Session.getDefaultInstance(props);
//		String username = (String) props.get("mail.smtps.user");
//		String password = (String) props.get("mail.smtps.password");

        MimeMessage message = new MimeMessage(mailSession);
        try {
            message.setFrom(new InternetAddress("k.ptak@budexpo.eu"));

            message.setSubject(subject);

            message.setRecipients(javax.mail.Message.RecipientType.TO,
                    javax.mail.internet.InternetAddress.parse(email, false));
            if (cc!=null){
            message.setRecipients(javax.mail.Message.RecipientType.CC,
                    javax.mail.internet.InternetAddress.parse(cc, false));
            }
            message.setText(body);


            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(body);

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            try {
                // attach the file to the message
                mbp2.attachFile(filename);
            } catch (IOException ex) {
                System.out.print("brak dostepu do zasoby");
                Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            // add the Multipart to the message
            message.setContent(mp);

            // set the Date: header
            message.setSentDate(new Date());


            message.saveChanges();
            System.out.print("PRZED zdefiniowany transport na smtp ");
            Transport transport = mailSession.getTransport("smtp");
            System.out.print("mamy zdefiniowany transport na smtps " + transport.toString());
            try {
                transport.connect();
                System.out.print("Po połączeniu ");

                transport.sendMessage(message, message.getAllRecipients());
                System.out.print("Wysłano maila");
//			Logger.getLogger(this.getClass()).warn("Message sent");
            } finally {
                transport.close();
            }
        } catch (MessagingException ex) {
            Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
