package academy.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void senMail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("totranhieu@gmail.com");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(toEmail);
        boolean html = true;
        mimeMessageHelper.setText(body,html);
        javaMailSender.send(mimeMessage);
    }
}
