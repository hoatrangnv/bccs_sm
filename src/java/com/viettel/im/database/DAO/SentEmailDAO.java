/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.sms.SmsClient;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author trungdh3_s
 */
public class SentEmailDAO extends BaseDAOAction {

    public static int SendMail(String smtpServer, String to, String from, String psw, String subject, String body) {
        try {
            if (to == null || to.trim().equals("")) {
                return 0;
            }
            String filename = "$JAVA_HOME/jre/lib/security/cacerts"; //System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar);
            System.setProperty("javax.net.ssl.trustStore", filename);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

            System.out.println("gui qua email :" + to);
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.starttls.enable", "false");
            final String login = from;//"nth001@gmail.com";//usermail
            final String pwd = psw;//"password cua ban o day";
            Authenticator pa = null; //default: no authentication
            if (login != null && pwd != null) { //authentication required?
                props.put("mail.smtp.auth", "true");
                pa = new Authenticator() {

                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, pwd);
                    }
                };
            }
            //chỗ này có thể cần thay đổi trung dh3
            Session session1 = Session.getInstance(props, pa);
            Message msg = new MimeMessage(session1);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
                    to, false));

            msg.setSubject(MimeUtility.encodeText(body, "UTF-8", "B"));
            MimeBodyPart messagePart = new MimeBodyPart();
            MimeMultipart multipart = new MimeMultipart();
            messagePart.setText(body);
//            messagePart.setHeader("Content-Type", "text/plain;  charset=\"utf-8\"");
//            msg.setHeader("Content-Type", "text/plain; charset=utf-8");
////            text/html;; x-java-content-handler=com.sun.mail.handlers.text_html
//            messagePart.setHeader("Content-Transfer-Encoding", "Base64");
            multipart.addBodyPart(messagePart);
            msg.setContent(multipart);
            msg.setHeader("X-Mailer", "LOTONtechEmail");
            msg.setSentDate(new Date());
            msg.saveChanges();
            Transport.send(msg);
            System.out.println("Message sent OK.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

        return 0;
    }

    public static void main(String[] args) {
//        SentEmailDAO t = new SentEmailDAO();
//      //  t.SendMail();
//        System.out.println("chạy");
//        String smtpServer = "smtp.viettel.com.vn";
//        String to = "trungdh3@viettel.com.vn";
//        String from = "trungdh3@viettel.com.vn";
//        String subject = "Chuc ban thi tot ";
//
//        //  String body = MessageFormat.format(getText("sms.0009"), "abc", "!23", "11/11/1111");
//        String body = "xin chào";
//        String psw = "huutrung.no1";
//        testmail test = new testmail();
//        t.SendMail(smtpServer, to, from, psw, subject, body);
//        System.out.println("chay ok");
    }
}
