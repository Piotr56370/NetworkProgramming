import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

class TLSEmail {

    /**
     Outgoing Mail (SMTP) Server
     requires TLS or SSL: smtp.gmail.com (use authentication)
     Use Authentication: Yes
     Port for TLS/STARTTLS: 587
     */

    private User userInstance;

    TLSEmail(User user) {
        this.userInstance = user;
        final String toEmail = "vasile.drumea@endava.com";

        System.out.println("TLSEmail Starts...");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //SMTP Host
        props.put("mail.smtp.port", "587");             //TLS Port
        props.put("mail.smtp.auth", "true");            //Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //Enable STARTTLS

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userInstance.getEmail(), userInstance.getPassword());
            }
        };

        Session session = Session.getInstance(props, auth);
        EmailUtil.sendAttachmentEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
    }
}
