# Course: *Network Programming*
------
# Topic: *SMTP Client App*
------
## Objectives :
1. Study the SMTP and POP3 protocols and their usages in Web communication;

2. Find out about the Java components responsible for implementing interactions with the required protocols;

3. Create a client app which supports sending and reading emails using an account;

## Additional tasks :
1. Sending an email with attachments;

2. Sending an email in HTML format with images;

3. Creating a class, POP3Header which facilitates reading headers from message(stored in a dictionary);

## Theory :

### Email Protocols Overview

Email Protocols: IMAP, POP3, SMTP;

Basicaly, a protocol is a standard method used at each end of a communication channel, in order to properly transmit information. In order to deal with your email you must use a client to access a mail server. The mail client and mail server can exchange information with each other using a variety of protocols.

**Post Office Protocol version 3 (POP3)** is a standard mail protocol used to receive emails from a remote server to a local email client. POP3 allows you to download email messages on your local computer and read them even when you are offline. Note, that when you use POP3 to connect to your email account, messages are downloaded locally and removed from the email server.

By default, the POP3 protocol works on two ports:

  * Port 110 - this is the default POP3 non-encrypted port;
  
  * Port 995 - this is the port you need to use if you want to connect using POP3 securely;

**Internet Message Access Protocol (IMAP)** is a mail protocol used for accessing email on a remote web server from a local client. 

While the POP3 protocol assumes that your email is being accessed only from one application, IMAP allows simultaneous access by multiple clients. This is why IMAP is more suitable for you if you're going to access your email from different locations or if your messages are managed by multiple users.

By default, the IMAP protocol works on two ports:

  * Port 143 - this is the default IMAP non-encrypted port;
  
  * Port 993 - this is the port you need to use if you want to connect using IMAP securely;

**Simple Mail Transfer Protocol (SMTP)** is the standard protocol for sending emails across the Internet.

By default, the SMTP protocol works on three ports:

  * Port 25 - this is the default SMTP non-encrypted port;
  
  * Port 2525 - this port is opened on all SiteGround servers in case port 25 is filtered (by your ISP for example) and you want to send non-encrypted emails with SMTP;
  
  * Port 465 - this is the port used if you want to send messages using SMTP securely;

## Implementation :

The client app is composed by the mai object __*User*__ in which we store the email and password, and 3 functionalities. Two of them are for sending and email and the other for reading one. 

For sending the emails I've used 2 methods which require authentication which are TLS and SSL. In each of them I use the instance of the user to create an email and to send it to the email __*toEmail*__. 

The difference between the 2 methods appears at the properties set to each one : 

### For TLS :


~~~
props.put("mail.smtp.host", "smtp.gmail.com");  //SMTP Host
props.put("mail.smtp.port", "587");             //TLS Port
props.put("mail.smtp.auth", "true");            //Enable authentication
props.put("mail.smtp.starttls.enable", "true"); //Enable STARTTLS
~~~

### For SSL : 


~~~
props.put("mail.smtp.host", "smtp.gmail.com");                                //SMTP Host
props.put("mail.smtp.socketFactory.port", "465");                             //SSL Port
props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
props.put("mail.smtp.auth", "true");                                          //Enabling SMTP Authentication
props.put("mail.smtp.port", "465"); 
~~~

After the properties are set and the authentication is done I use an utility from __*EmailUtil*__ class to send the email. The method is independent of the properties, just creates the message and sends it :

~~~
MimeMessage msg = new MimeMessage(session);
//set message headers
msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
msg.addHeader("format", "flowed");
msg.addHeader("Content-Transfer-Encoding", "8bit");

msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
msg.setSubject(subject, "UTF-8");
msg.setText(body, "UTF-8");
msg.setSentDate(new Date());
msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

Transport.send(msg);
~~~

To read an email I use the following properties : 

~~~
props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
props.put("mail.pop3.socketFactory.port", "995");
props.put("mail.pop3.port", "995");
props.put("mail.pop3.host", "pop.gmail.com");
props.put("mail.pop3.user", user.getEmail());
props.put("mail.store.protocol", "pop3");
Session session = Session.getInstance(props);
~~~

Then I connect to the host server with the credentials to get the inbox and from it the message. When I have the message I print it with the __*printMessage*__ method. To get the body of the message I'll have to check if it is plain text, in which case I just get the text, but if it is multipart text I'll have to decompose it. For it I used a function which traverses all the parts and appends them to the resulting string : 


~~~
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
~~~

