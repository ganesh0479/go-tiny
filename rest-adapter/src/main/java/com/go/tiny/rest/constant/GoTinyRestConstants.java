package com.go.tiny.rest.constant;

public interface GoTinyRestConstants {
  String CANT_READ_MULTIPART_FILE = "Unable to load picture";
  String AUTHORIZED = "AUTHORIZED";
  String APPROVED = "APPROVED";
  String CARD_HEADER =
      "<table style=\"width: 300px;height: 200px;font-family: 'Calibri'\">\n"
          + "  <tr>\n"
          + "    <th style=\"background-color: black; color: white;height:75px;font-size: 30px\">\n"
          + "      <b>";
  String CARD_BODY =
      "</b>\n" + "    </th>\n" + "  </tr>\n" + "  <tr>\n" + "    <td>\n" + "      <b>";
  String CARD_FOOTER =
      "</b>\n"
          + "    </td>\n"
          + "  </tr>\n"
          + "  <tr style=\"height: 40px\">\n"
          + "    <th style=\"background-color: black; color: white;\">\n"
          + "      <a href=\"";
  String MAIL_TEMPLATE_END =
      "\"><b>Go Tiny</b></a>\n" + "    </th>\n" + "  </tr>\n" + "</table>\n" + "</body></html>";
  String MAIL_TEMPLATE_START =
      "<!DOCTYPE html>\n"
          + "<html lang=\"en\">\n"
          + "<head>\n"
          + "  <meta charset=\"UTF-8\">\n"
          + "  <title>Title</title>\n"
          + "</head>"
          + "<body>Hi,<br><br>Please find the </b>";
  String MAIL_BODY = "<b> bookmark. Please click on Go Tiny to visit webpage.<br><br>";
}
