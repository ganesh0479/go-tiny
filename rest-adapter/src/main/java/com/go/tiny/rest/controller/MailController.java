package com.go.tiny.rest.controller;

import com.go.tiny.rest.model.Status;
import com.go.tiny.rest.model.TinyMail;
import com.go.tiny.rest.model.TinyMailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

  @Operation(summary = "Send mail to the user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mail Sent",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Status.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Unable to send mail to the users",
            content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error. Unable to send mail to the users",
            content = @Content)
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
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
