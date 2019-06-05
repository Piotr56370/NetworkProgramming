import org.jsoup.Jsoup;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.Enumeration;
import java.util.Properties;

class ReadEmail {

    private Message[] messages;

    ReadEmail(User user) {
        String host = "pop.gmail.com"; //POP3 Host

        Properties props = new Properties();
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.port", "995");
        props.put("mail.pop3.port", "995");
        props.put("mail.pop3.host", "pop.gmail.com");
        props.put("mail.pop3.user", user.getEmail());
        props.put("mail.store.protocol", "pop3");
        Session session = Session.getInstance(props);

        try{
            //Get reference to the inbox folder
            Store store = session.getStore("pop3");
            store.connect(host, user.getEmail(), user.getPassword());
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            //Get list of messages
            messages = inbox.getMessages();

            if (messages.length == 0) {
                System.out.println("Inbox contains no messages!");
            }

            printMessage(0);
            inbox.close(true);
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void printMessage(int id) {
        try {
            System.out.println("~~~ Message number " + id);
            System.out.println("~~~ From : " + messages[id].getFrom()[0]);
            System.out.println("~~~ Subject : " + messages[id].getSubject());
            System.out.println("~~~ Sent on : " + messages[id].getSentDate());
            System.out.println("~~~ Body : " + getMessageBody(messages[id]));
            System.out.println("---------------------------------------------");
            System.out.println("~~~ Headers : ");
            Enumeration e = messages[id].getAllHeaders();
            while (e.hasMoreElements()) {
                Header header = (Header) e.nextElement();
                System.out.println(header.getName() + " : " + header.getValue());
            }
            System.out.println("\n");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getMessageBody(Message message) throws Exception {
        String body = "";

        if (message.isMimeType("text/plain")) {
            body = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            body = getBodyFromMimeMultipart(mimeMultipart);
        }

        return body;
    }

    private static String getBodyFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder multipartBody = new StringBuilder();

        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                multipartBody.append(bodyPart.getContent());
                break;
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                multipartBody.append(Jsoup.parse(html).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                multipartBody.append(getBodyFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }

        return multipartBody.toString();
    }
}