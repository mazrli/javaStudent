package student.javastudent.Email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailService implements EmailSender{
    private JavaMailSender mailSender;
    @Override
    public void send(String to, String content){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utd-8");
            helper.setText(content, true);
            helper.setTo(to);
            helper.setFrom("aziz@gmail.com");
            helper.setSubject("Confirm email");
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("failed to send email");
        }
    }
}
