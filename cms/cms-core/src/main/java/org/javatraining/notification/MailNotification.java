package org.javatraining.notification;

import org.apache.velocity.app.VelocityEngine;
import org.javatraining.model.PersonVO;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The project name is cms.
 * Created by sergey on 30.06.15 at 18:06.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class MailNotification implements NotificationService<PersonVO> {
    private JavaMailSenderImpl sender;
    private VelocityEngine velocityEngine;

    private static VelocityEngine createEngine() throws Exception {
        VelocityEngine ve = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.init(p);
        return ve;
    }

    @Override
    public void sendUserProperties(String from, PersonVO user) {
        sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setUsername("codedealerb");
        sender.setPassword("");//insert pass
        try {
            velocityEngine = createEngine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(new InternetAddress(user.getEmail()));
            helper.setFrom(new InternetAddress(from));
            helper.setSentDate(new Date());
            helper.setSubject("");//set subject
            Map model = new HashMap();
            model.put("personProperties", user);

            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "../resources/velocity/mail.html", "UTF-8", model);
            helper.setText(text, true);
        };
        sender.send(preparator);
    }
}
