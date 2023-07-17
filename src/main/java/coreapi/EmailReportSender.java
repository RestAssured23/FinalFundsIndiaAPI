package coreapi;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailReportSender {
    public static void main(String[] args) {
        String emailReportPath = "C:\\Users\\Fi-User\\fi-repositories\\fi-test-automation\\test-output\\emailable-report.html";
        // JavaMail: Send the email with the attachment
        String smtpHost = "smtp.example.com";
        int smtpPort = 587;
        String senderEmail = "your_email@example.com";
        String senderPassword = "your_password";
        String receiverEmail = "recipient@example.com";
        String subject = "Generated Email Report";
        String body = "Please find the attached email report.";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Add attachment
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(emailReportPath);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while sending the email: " + e.getMessage());
        }
    }
}

