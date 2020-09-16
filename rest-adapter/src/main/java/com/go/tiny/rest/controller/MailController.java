package com.go.tiny.rest.controller;

import com.go.tiny.rest.model.Status;
import com.go.tiny.rest.model.TinyMail;
import com.go.tiny.rest.model.TinyMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import static com.go.tiny.rest.constant.GoTinyRestConstants.*;

@RestController
@RequestMapping("/api/v1/go-tiny/mails")
@CrossOrigin(origins = "*")
public class MailController {
  @Autowired private JavaMailSenderImpl javaMailSender;

  @PostMapping
  public ResponseEntity<Status> sendMails(@RequestBody TinyMail tinyMail) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(tinyMail.getFrom());
    message.setTo(tinyMail.getTo());
    if (!CollectionUtils.isEmpty(tinyMail.getMailRequests())) {
      tinyMail
          .getMailRequests()
          .forEach(
              tinyMailRequest -> {
                message.setSubject(tinyMailRequest.getTitle() + " Bookmarks");
                message.setText(setMailBody(tinyMailRequest));
                javaMailSender.send(message);
              });
    }
    return ResponseEntity.ok(Status.builder().status("Mail Sent").build());
  }

  private String setMailBody(final TinyMailRequest tinyMailRequest) {
    return MAIL_TEMPLATE_START
        + tinyMailRequest.getTitle()
        + MAIL_BODY
        + CARD_HEADER
        + tinyMailRequest.getTitle()
        + CARD_BODY
        + tinyMailRequest.getDescription()
        + CARD_FOOTER
        + tinyMailRequest.getTinyUrl()
        + MAIL_TEMPLATE_END;
  }
}
