package Util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    
    static final String FROM = "webmaster@puerto-quetzal.com";
    static final String FROMNAME = "EMPRESA PORTUARIA QUETZAL";
    static final String SMTP_USERNAME = "webmaster@puerto-quetzal.com";
    static final String SMTP_PASSWORD = "guatemala*21";//"master18*";
    static final String CONFIG = "Configset";
    static final String HOST = "mail.puerto-quetzal.com";
    static final int PORT = 587;
    //static final int PORT = 465;
    static final String SUBJECT = "CITA REGISTRADA";
    static final String SUBJECT2 = "USUARIO REGISTRADO";
    static final String SUBJECT3 = "CAMBIO DE CLAVE";
    private String BODY = "";
    
public void Enviar(String TO,String licencia, String piloto, String empresa, String placa) throws Exception {
    BODY = String.join(System.getProperty("line.separator"),
            "<h2>AVISO</h2><br>",
            "-----------------------------------",
            "<h2>EMPRESA..: </h2>",empresa,"<br>",
            "-----------------------------------",
            "<h2>PILOTO...: </h2>",piloto,"<br>",
            "-----------------------------------",
            "<h2>LICENCIA.: </h2>",licencia,"<br>",
            "-----------------------------------",
            "<h2>PLACA.: </h2>",placa,"<br>",
            "-----------------------------------",
            "<h1>SISTEMA CITAS WEB</h1><br>",
            "<h1>AREA DE SISTEMAS - M. Sc. Federico Augusto Yaque Castillo</h1><br>",
            "<h1>EMPRESA PORTUARIA QUETZAL</h1>");
        Properties props = System.getProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.port", PORT);
        //props.put("mail.smtp.socketFactory.port", PORT);
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.disable", "true");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.ssl.trust", HOST);
        
        Session session = Session.getDefaultInstance(props);
        
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIG);
        Transport transport = session.getTransport();
        
        try {
            System.out.println("Enviando Mensaje..!");
            transport.connect(HOST,SMTP_USERNAME,SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Mensaje Enviado..!");
        } catch (Exception e) {
            System.out.println("ERROR.: "+e.getMessage());
        }
        finally {
            transport.close();
        }
    }

    public void EnviarPass(String TO,String usuario, String clave) throws Exception {
    BODY = String.join(System.getProperty("line.separator"),
            "<h2>CREACION DE USUARIO</h2><br>",
            "-----------------------------------",
            "<h2>USUARIO..: </h2>",usuario,"<br>",
            "-----------------------------------",
            "<h2>CLAVE...: </h2>",clave,"<br>",
            "-----------------------------------",
            "<h1>SISTEMA CITAS WEB</h1><br>",
            "<h1>AREA DE SISTEMAS - M. Sc. Federico Augusto Yaque Castillo</h1><br>",
            "<h1>EMPRESA PORTUARIA QUETZAL</h1><br>",
            "<h1>UNIDAD DE INFORMATICA - </h1>");
        Properties props = System.getProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.port", PORT);
        //props.put("mail.smtp.socketFactory.port", PORT);
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.disable", "true");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.ssl.trust", HOST);
        
        Session session = Session.getDefaultInstance(props);
        
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        //msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
        msg.setSubject(SUBJECT2);
        msg.setContent(BODY,"text/html");
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIG);
        Transport transport = session.getTransport();
        
        try {
            System.out.println("Enviando Mensaje..!");
            transport.connect(HOST,SMTP_USERNAME,SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Mensaje Enviado..!");
        } catch (Exception e) {
            System.out.println("ERROR.: "+e.getMessage()+" --> "+TO);
        }
        finally {
            transport.close();
        }
    }

    public void EnviarResetPass(String TO,String usuario, String clave) throws Exception {
    BODY = String.join(System.getProperty("line.separator"),
            "<h2>CAMBIO DE CLAVE</h2><br>",
            "-----------------------------------",
            "<h2>USUARIO..: </h2>",usuario,"<br>",
            "-----------------------------------",
            "<h2>CLAVE...: </h2>",clave,"<br>",
            "-----------------------------------",
            "<h1>SISTEMA CITAS WEB</h1><br>",
            "<h1>AREA DE SISTEMAS - M. Sc. Federico Augusto Yaque Castillo</h1><br>",
            "<h1>EMPRESA PORTUARIA QUETZAL</h1>");
        Properties props = System.getProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.port", PORT);
        //props.put("mail.smtp.socketFactory.port", PORT);
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.disable", "true");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.ssl.trust", HOST);
        
        Session session = Session.getDefaultInstance(props);
        
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO));
        msg.setSubject(SUBJECT3);
        msg.setContent(BODY,"text/html");
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIG);
        Transport transport = session.getTransport();
        
        try {
            System.out.println("Enviando Mensaje..!");
            transport.connect(HOST,SMTP_USERNAME,SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Mensaje Enviado..!");
        } catch (Exception e) {
            System.out.println("ERROR.: "+e.getMessage()+" --> "+TO);
        }
        finally {
            transport.close();
        }
    }
    
    
}
