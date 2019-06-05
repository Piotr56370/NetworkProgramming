import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

class SSLEmail {

    /**
     Outgoing Mail (SMTP) Server
     requires TLS or SSL: smtp.gmail.com (use authentication)
     Use Authentication: Yes
     Port for SSL: 465
     */

    private User userInstance;

    SSLEmail(final User user) {
        this.userInstance = user;
        final String toEmail = "vasile.drumea@endava.com";

        System.out.println("SSLEmail Starts...");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");                                //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465");                             //SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true");                                          //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465");                                           //SMTP Port

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userInstance.getEmail(), user.getPassword());
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");

        EmailUtil.sendImageEmail(session, toEmail,"SSLEmail Testing Subject", "SSLEmail Testing Body");
    }
}
