package com.nmt.FoodOrderAPI.service.email;

import com.nmt.FoodOrderAPI.dto.CompletedBillNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendCompletedBillNotificationEmail(String customerEmail, CompletedBillNotification completedBillNotification)
            throws MessagingException {
        Context context = new Context();
        context.setVariable("billId", completedBillNotification.getPendingPrepaidBillId());
        context.setVariable("billItems", completedBillNotification.getCompletedBillItemList());
        context.setVariable("totalPrice", completedBillNotification.getTotalPrice());
        context.setVariable("completedTime", completedBillNotification.getCompletedTime());

        String htmlContent = templateEngine.process("EmailTemplate", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(customerEmail);
        helper.setSubject("Đơn hàng đã hoàn thành");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
