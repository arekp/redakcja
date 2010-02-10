/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.newsLettera;
import bean.newsLetteraFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author arekp
 */
@Stateless
public class sendMailBean implements sendMailLocal {

    static String fromFmt;
    @PersistenceContext
    private EntityManager em;
    static String subjFmt;

    public void sendMsg(String email, String cc, String subject, String body, String filename) {
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
            if (cc != null) {
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
            System.out.print("bład przy wysłaniu maila");
            Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, null, ex);
            try {
                throw new Exception("bład przy wysłaniu maila");
            } catch (Exception ex1) {
                Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }


    }

    public void getNews() throws NoSuchProviderException, MessagingException {
        InitialContext ictx;
        javax.mail.Session mailSession = null;
        try {
            ictx = new InitialContext();
//             mailSession = (javax.mail.Session) ictx.lookup("mail/MailTest");
            mailSession = (javax.mail.Session) ictx.lookup("mail/MailGoogle");
        } catch (NamingException ex) {
            System.out.print("blad pobierania JNDI dla mail");
            Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, "blad pobierania JNDI dla mail", ex);
        }

   System.out.print("-->"+ mailSession.getProperty("User")+" -- "+ mailSession.getProperty("mail.pop3.host"));


            Store store = mailSession.getStore();
            if(store.isConnected()) {
                System.out.println("JESTESMY Connected...");
            }else {
            store.connect();
            }
            System.out.println("Connected...");

      
//                Folder folder = store.getFolder("INBOX");
//        folder.open(Folder.READ_ONLY);
//
//        Message[] messages = folder.getMessages();

//        System.out.print("Wiadomosci w skrzynce " + messages.length);
//        for (int i = 0; i < messages.length; i++) {
//            System.out.println(
//                    i + " : " +
//                    messages[i].getFrom()[0] + "\t" +
//                    messages[i].getSubject() + "\t" + messages[i].getFlags().toString() + "\t"+
//                    messages[i].getSentDate() + "\n\n");
//
//        }
//        folder.close(true);
//        store.close();


//        Logger.getLogger(sendMailBean.class.getName()).log(Level.INFO, "Mamy store do pop3");

     
//        folder.open(Folder.READ_ONLY);
//
//        if (folder == null) {
//            Logger.getLogger(sendMailBean.class.getName()).log(Level.INFO, "Brak domyslengo folder");
//        }

         Folder folder = store.getDefaultFolder();
        folder = folder.getFolder("INBOX");

        folder.open(Folder.READ_ONLY);

        Message[] messages = folder.getMessages();

        System.out.print("Wiadomosci w skrzynce " + messages.length);

        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            String tytul = message.getSubject();
            System.out.print(" ----> tytul maila to " + tytul+" --> "+message.getFlags().toString());

            String szablon = "newslettera";
            if ((tytul.indexOf(szablon)) != -1) {
//              String from = ((InternetAddress) message.getFrom()[0]).getPersonal();
//                String adres = ((InternetAddress) message.getFrom()[0]).getAddress();
//                Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, " --- adres ---  " + adres);
//                Logger.getLogger(sendMailBean.class.getName()).log(Level.SEVERE, " ---------- ");
                this.printMessage(message);
            }

        }


        if (folder != null) {

            folder.close(false);
           System.out.print("Zamykamy folder ");
        }
        if (store != null) {
            store.close();
            mailSession=null;
            System.out.print("Zamykamy storage");
        }
    }

    private void printMessage(Message message) {
        try {
            // Get the header information
            String from = ((InternetAddress) message.getFrom()[0]).getPersonal();
            if (from == null) {
                from = ((InternetAddress) message.getFrom()[0]).getAddress();
            }
            System.out.println("FROM: " + from);
            String subject = message.getSubject();
            System.out.println("SUBJECT: " + subject);
            // -- Get the message part (i.e. the message itself) --
            Part messagePart = message;
            Object content = messagePart.getContent();
            // -- or its first body part if it is a multipart message --
            if (content instanceof Multipart) {
                messagePart = ((Multipart) content).getBodyPart(0);
//                System.out.println("[ Multipart Message ]");
            }
            // -- Get the content type --
            String contentType = messagePart.getContentType();
            // -- If the content is plain text, we can print it --
//            System.out.println("CONTENT:" + contentType);
            if (contentType.startsWith("text/plain") || contentType.startsWith("text/html")) {
                InputStream is = messagePart.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String thisLine = reader.readLine();
                String wiadomosc = "";
                while (thisLine != null) {
                    wiadomosc += thisLine+"\n";
                    if ((thisLine.indexOf("E-mail:")) != -1){
                    String [] temp =thisLine.split(":");
                    from = temp[1];
                    }
//                    System.out.println(thisLine);
                    thisLine = reader.readLine();
                }
                newsLettera news = new newsLettera();
                news.setAdres(from);
                news.setBody(wiadomosc);
                news.setDataWpisu(new Date());
//                System.out.println("-----------------------------" + news.getAdres() + " -- " + news.getBody()+"-----------------------------------");

//                newsLetteraFacade facade = new newsLetteraFacade();
                   em.persist(news);
//                facade.create(news);
            }
            System.out.println("-----------KONIEC------------------");
        } catch (Exception ex) {
               System.out.print("blad w amalizie");
            ex.printStackTrace();
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
//   
}
