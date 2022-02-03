package quevedo.ServerBasket.services.identificacion;

import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import quevedo.ServerBasket.config.ConfigYaml;
import quevedo.ServerBasket.utils.constantes.CorreosStrings;

import java.util.Properties;

public class MandarMail {

    ConfigYaml configYaml;

    @Inject
    public MandarMail(ConfigYaml configYaml) {
        this.configYaml = configYaml;
    }

    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;



        mailServerProperties = System.getProperties();
        mailServerProperties.put(CorreosStrings.MAIL_SMTP_PORT, Integer.parseInt(CorreosStrings.PUERTO));
        mailServerProperties.put(CorreosStrings.MAIL_SMTP_AUTH, CorreosStrings.TRUE);
        mailServerProperties.put(CorreosStrings.MAIL_SMTP_SSL_TRUST, CorreosStrings.SMTP_GMAIL_COM);
        mailServerProperties.put(CorreosStrings.MAIL_SMTP_STARTTLS_ENABLE, CorreosStrings.TRUE);



        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        String emailBody = msg;
        generateMailMessage.setContent(emailBody, CorreosStrings.TEXT_HTML);



        Transport transport = getMailSession.getTransport(CorreosStrings.SMTP);


        transport.connect(configYaml.getHost(),
                configYaml.getUserCorreo(),
                configYaml.getPasswCorreo());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

}
