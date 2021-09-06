package markszhouc.spam.encoding;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailSender {

    public static void send(Image crypticTamuLogo) throws Exception {
        System.out.println("prepare to send");
        // Recipient's email ID needs to be mentioned.
        String to = EmailController.RECEIVING_EMAIL;
        // Sender's email ID needs to be mentioned
        String from = EmailController.SENDING_EMAIL;

        final String username = EmailController.SENDING_EMAIL;  //change accordingly
        final String password = EmailController.SENDING_PASSWORD;  //change accordingly

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("HowdyHack 2020 Aggie Tech Support");

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();

            String htmlText = getDefaultHTMLText();

            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // create temp file
            String path = EmailController.LOGO_PATH;
            int last = Math.max(0, Math.max(path.lastIndexOf("\\"), path.lastIndexOf("/")));
            path = path.substring(0,  last + 1) + "temporary_logo_kioawlktlkl.png";
            File outputImage = new File(path);
            ImageIO.write(SwingFXUtils.fromFXImage(crypticTamuLogo, null), "png", outputImage);

            //while (! outputImage.exists())
            //    Thread.sleep(1);

            // first image
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(outputImage);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image1>");
            multipart.addBodyPart(messageBodyPart);

            // second image
            messageBodyPart = new MimeBodyPart();
            fds = new FileDataSource("assets/feature-1-image-1.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image2>");
            multipart.addBodyPart(messageBodyPart);

            // third image
            messageBodyPart = new MimeBodyPart();
            fds = new FileDataSource("assets/feature-1-image-2.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image3>");
            multipart.addBodyPart(messageBodyPart);

            // fourth image
            messageBodyPart = new MimeBodyPart();
            fds = new FileDataSource("assets/feature-1-image-3.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image4>");
            multipart.addBodyPart(messageBodyPart);

            // fifth image
            messageBodyPart = new MimeBodyPart();
            fds = new FileDataSource("assets/facebook-dark-gray.png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image5>");
            multipart.addBodyPart(messageBodyPart);

            // sixth image
            messageBodyPart = new MimeBodyPart();
            fds = new FileDataSource("assets/twitter-dark-gray.png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image6>");
            multipart.addBodyPart(messageBodyPart);

            // seventh image
            messageBodyPart = new MimeBodyPart();
            fds = new FileDataSource("assets/instagram-dark-gray.png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image7>");
            multipart.addBodyPart(messageBodyPart);

            // eighth image
            messageBodyPart = new MimeBodyPart();
            fds = new FileDataSource("assets/pinterest-dark-gray.png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image8>");
            multipart.addBodyPart(messageBodyPart);

            //outputImage.delete();

            // put everything together
            message.setContent(multipart);
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

            outputImage.deleteOnExit();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDefaultHTMLText() {
        return"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">"+
                "<head>"+
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"+
                "  <!--[if !mso]><!-->"+
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"+
                "  <!--<![endif]-->"+
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
                "  <meta name=\"format-detection\" content=\"telephone=no\">"+
                "  <meta name=\"x-apple-disable-message-reformatting\">"+
                "  <title></title>"+
                "  <style type=\"text/css\">"+
                "    @media screen {"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 400;"+
                "        src: local('Fira Sans Regular'), local('FiraSans-Regular'), url(https://fonts.gstatic.com/s/firasans/v8/va9E4kDNxMZdWfMOD5Vvl4jLazX3dA.woff2) format('woff2');"+
                "        unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;"+
                "      }"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 400;"+
                "        src: local('Fira Sans Regular'), local('FiraSans-Regular'), url(https://fonts.gstatic.com/s/firasans/v8/va9E4kDNxMZdWfMOD5Vvk4jLazX3dGTP.woff2) format('woff2');"+
                "        unicode-range: U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;"+
                "      }"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 500;"+
                "        src: local('Fira Sans Medium'), local('FiraSans-Medium'), url(https://fonts.gstatic.com/s/firasans/v8/va9B4kDNxMZdWfMOD5VnZKveRhf6Xl7Glw.woff2) format('woff2');"+
                "        unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;"+
                "      }"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 500;"+
                "        src: local('Fira Sans Medium'), local('FiraSans-Medium'), url(https://fonts.gstatic.com/s/firasans/v8/va9B4kDNxMZdWfMOD5VnZKveQhf6Xl7Gl3LX.woff2) format('woff2');"+
                "        unicode-range: U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;"+
                "      }"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 700;"+
                "        src: local('Fira Sans Bold'), local('FiraSans-Bold'), url(https://fonts.gstatic.com/s/firasans/v8/va9B4kDNxMZdWfMOD5VnLK3eRhf6Xl7Glw.woff2) format('woff2');"+
                "        unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;"+
                "      }"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 700;"+
                "        src: local('Fira Sans Bold'), local('FiraSans-Bold'), url(https://fonts.gstatic.com/s/firasans/v8/va9B4kDNxMZdWfMOD5VnLK3eQhf6Xl7Gl3LX.woff2) format('woff2');"+
                "        unicode-range: U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;"+
                "      }"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 800;"+
                "        src: local('Fira Sans ExtraBold'), local('FiraSans-ExtraBold'), url(https://fonts.gstatic.com/s/firasans/v8/va9B4kDNxMZdWfMOD5VnMK7eRhf6Xl7Glw.woff2) format('woff2');"+
                "        unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;"+
                "      }"+
                "      @font-face {"+
                "        font-family: 'Fira Sans';"+
                "        font-style: normal;"+
                "        font-weight: 800;"+
                "        src: local('Fira Sans ExtraBold'), local('FiraSans-ExtraBold'), url(https://fonts.gstatic.com/s/firasans/v8/va9B4kDNxMZdWfMOD5VnMK7eQhf6Xl7Gl3LX.woff2) format('woff2');"+
                "        unicode-range: U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;"+
                "      }"+
                "    }"+
                ""+
                "    #outlook a {"+
                "      padding: 0;"+
                "    }"+
                ""+
                "    .ReadMsgBody,"+
                "    .ExternalClass {"+
                "      width: 100%;"+
                "    }"+
                ""+
                "    .ExternalClass,"+
                "    .ExternalClass p,"+
                "    .ExternalClass td,"+
                "    .ExternalClass div,"+
                "    .ExternalClass span,"+
                "    .ExternalClass font {"+
                "      line-height: 100%;"+
                "    }"+
                ""+
                "    div[style*=\"margin: 14px 0\"],"+
                "    div[style*=\"margin: 16px 0\"] {"+
                "      margin: 0 !important;"+
                "    }"+
                ""+
                "    table,"+
                "    td {"+
                "      mso-table-lspace: 0;"+
                "      mso-table-rspace: 0;"+
                "    }"+
                ""+
                "    table,"+
                "    tr,"+
                "    td {"+
                "      border-collapse: collapse;"+
                "    }"+
                ""+
                "    body,"+
                "    td,"+
                "    th,"+
                "    p,"+
                "    div,"+
                "    li,"+
                "    a,"+
                "    span {"+
                "      -webkit-text-size-adjust: 100%;"+
                "      -ms-text-size-adjust: 100%;"+
                "      mso-line-height-rule: exactly;"+
                "    }"+
                ""+
                "    img {"+
                "      border: 0;"+
                "      outline: none;"+
                "      line-height: 100%;"+
                "      text-decoration: none;"+
                "      -ms-interpolation-mode: bicubic;"+
                "    }"+
                ""+
                "    a[x-apple-data-detectors] {"+
                "      color: inherit !important;"+
                "      text-decoration: none !important;"+
                "    }"+
                ""+
                "    body {"+
                "      margin: 0;"+
                "      padding: 0;"+
                "      width: 100% !important;"+
                "      -webkit-font-smoothing: antialiased;"+
                "    }"+
                ""+
                "    .pc-gmail-fix {"+
                "      display: none;"+
                "      display: none !important;"+
                "    }"+
                ""+
                "    @media screen and (min-width: 621px) {"+
                "      .pc-email-container {"+
                "        width: 620px !important;"+
                "      }"+
                "    }"+
                ""+
                "    @media screen and (max-width:620px) {"+
                "      .pc-sm-p-24-20-30 {"+
                "        padding: 24px 20px 30px !important"+
                "      }"+
                "      .pc-sm-p-35-10-15 {"+
                "        padding: 35px 10px 15px !important"+
                "      }"+
                "      .pc-sm-mw-50pc {"+
                "        max-width: 50% !important"+
                "      }"+
                "      .pc-sm-p-21-10-14 {"+
                "        padding: 21px 10px 14px !important"+
                "      }"+
                "      .pc-sm-h-20 {"+
                "        height: 20px !important"+
                "      }"+
                "      .pc-sm-mw-100pc {"+
                "        max-width: 100% !important"+
                "      }"+
                "    }"+
                ""+
                "    @media screen and (max-width:525px) {"+
                "      .pc-xs-p-15-10-20 {"+
                "        padding: 15px 10px 20px !important"+
                "      }"+
                "      .pc-xs-h-100 {"+
                "        height: 100px !important"+
                "      }"+
                "      .pc-xs-br-disabled br {"+
                "        display: none !important"+
                "      }"+
                "      .pc-xs-fs-30 {"+
                "        font-size: 30px !important"+
                "      }"+
                "      .pc-xs-lh-42 {"+
                "        line-height: 42px !important"+
                "      }"+
                "      .pc-xs-p-25-0-5 {"+
                "        padding: 25px 0 5px !important"+
                "      }"+
                "      .pc-xs-mw-100pc {"+
                "        max-width: 100% !important"+
                "      }"+
                "      .pc-xs-p-5-0 {"+
                "        padding: 5px 0 !important"+
                "      }"+
                "    }"+
                "  </style>"+
                "  <!--[if mso]>"+
                "    <style type=\"text/css\">"+
                "        .pc-fb-font {"+
                "            font-family: Helvetica, Arial, sans-serif !important;"+
                "        }"+
                "    </style>"+
                "    <![endif]-->"+
                "  <!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->"+
                "</head>"+
                "<body style=\"width: 100% !important; margin: 0; padding: 0; mso-line-height-rule: exactly; -webkit-font-smoothing: antialiased; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; background-color: #f4f4f4\" class=\"\">"+
                "  <div style=\"display: none !important; visibility: hidden; opacity: 0; overflow: hidden; mso-hide: all; height: 0; width: 0; max-height: 0; max-width: 0; font-size: 1px; line-height: 1px; color: #151515;\"></div>"+
                "  <div style=\"display: none !important; visibility: hidden; opacity: 0; overflow: hidden; mso-hide: all; height: 0; width: 0; max-height: 0; max-width: 0;\">"+
                "    ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ ‌ "+
                "  </div>"+
                "  <table class=\"pc-email-body\" width=\"100%\" bgcolor=\"#f4f4f4\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"table-layout: fixed;\">"+
                "    <tbody>"+
                "      <tr>"+
                "        <td class=\"pc-email-body-inner\" align=\"center\" valign=\"top\">"+
                "          <!--[if gte mso 9]>"+
                "            <v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">"+
                "                <v:fill type=\"tile\" src=\"\" color=\"#f4f4f4\"/>"+
                "            </v:background>"+
                "            <![endif]-->"+
                "          <!--[if (gte mso 9)|(IE)]><table width=\"620\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"><tr><td width=\"620\" align=\"center\" valign=\"top\"><![endif]-->"+
                "          <table class=\"pc-email-container\" width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"margin: 0 auto; max-width: 620px;\">"+
                "            <tbody>"+
                "              <tr>"+
                "                <td align=\"left\" valign=\"top\" style=\"padding: 0 10px;\">"+
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">"+
                "                    <tbody>"+
                "                      <tr>"+
                "                        <td height=\"20\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                      </tr>"+
                "                    </tbody>"+
                "                  </table>"+
                "                  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">"+
                "                    <tbody>"+
                "                      <tr>"+
                "                        <td valign=\"top\">"+
                "                          <!-- BEGIN MODULE: Header 1 -->"+
                "                          <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">"+
                "                            <tbody>"+
                "                              <tr>"+
                "                                <td bgcolor=\"#1B1B1B\" valign=\"top\" style=\"background-color: #1B1B1B; background-position: top center; background-size: cover\">"+
                "                                  <!--[if gte mso 9]>"+
                "            <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"width: 600px;\">"+
                "                <v:fill type=\"frame\" src=\"\" color=\"#1B1B1B\"></v:fill>"+
                "                <v:textbox style=\"mso-fit-shape-to-text: true;\" inset=\"0,0,0,0\">"+
                "                    <div style=\"font-size: 0; line-height: 0;\">"+
                "                        <table width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" align=\"center\">"+
                "                            <tr>"+
                "                                <td style=\"font-size: 14px; line-height: 1.5;\" valign=\"top\">"+
                "                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">"+
                "                                        <tr>"+
                "                                            <td colspan=\"3\" height=\"24\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                        </tr>"+
                "                                        <tr>"+
                "                                            <td width=\"30\" valign=\"top\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                            <td valign=\"top\" align=\"left\">"+
                "            <![endif]-->"+
                "                                  <!--[if !gte mso 9]><!-->"+
                "                                  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\">"+
                "                                    <tbody>"+
                "                                      <tr>"+
                "                                        <td class=\"pc-sm-p-24-20-30 pc-xs-p-15-10-20\" valign=\"top\" style=\"padding: 24px 30px 40px;\">"+
                "                                          <!--<![endif]-->"+
                "                                          <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">"+
                "                                            <tbody>"+
                "                                              <tr>"+
                "                                                <td valign=\"top\" style=\"padding: 10px;\">"+
                "                                                  <a href=\"http://example.com\" style=\"text-decoration: none;\"><img src=\"cid:image1\" width=\"520\" height=\"416\" alt=\"\" style=\"max-width: 100%; border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; font-size: 14px; color: #ffffff; height: auto\"></a>"+
                "                                                </td>"+
                "                                              </tr>"+
                "                                              <tr>"+
                "                                                <td class=\"pc-xs-h-100\" height=\"0\" style=\"line-height: 1px; font-size: 1px\"> </td>"+
                "                                              </tr>"+
                "                                              <tr>"+
                "                                                <td class=\"pc-xs-fs-30 pc-xs-lh-42 pc-fb-font\" valign=\"top\" style=\"padding: 13px 10px 0; letter-spacing: -0.7px; line-height: 46px; font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 36px; font-weight: 800; color: #ffffff;\">Aggie Tech Support</td>"+
                "                                              </tr>"+
                "                                            </tbody>"+
                "                                          </table>"+
                "                                          <!--[if !gte mso 9]><!-->"+
                "                                        </td>"+
                "                                      </tr>"+
                "                                    </tbody>"+
                "                                  </table>"+
                "                                  <!--<![endif]-->"+
                "                                  <!--[if gte mso 9]>"+
                "                                            </td>"+
                "                                            <td width=\"30\" style=\"line-height: 1px; font-size: 1px;\" valign=\"top\"> </td>"+
                "                                        </tr>"+
                "                                        <tr>"+
                "                                            <td colspan=\"3\" height=\"40\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                        </tr>"+
                "                                    </table>"+
                "                                </td>"+
                "                            </tr>"+
                "                        </table>"+
                "                    </div>"+
                "                </v:textbox>"+
                "            </v:rect>"+
                "            <![endif]-->"+
                "                                </td>"+
                "                              </tr>"+
                "                            </tbody>"+
                "                          </table>"+
                "                          <!-- END MODULE: Header 1 -->"+
                "                          <!-- BEGIN MODULE: Feature 1 -->"+
                "                          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                            <tbody>"+
                "                              <tr>"+
                "                                <td class=\"pc-sm-p-35-10-15 pc-xs-p-25-0-5\" style=\"padding: 40px 20px; background-color: #ffffff\" valign=\"top\" bgcolor=\"#ffffff\">"+
                "                                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                    <tbody>"+
                "                                      <tr>"+
                "                                        <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 24px; font-weight: 700; line-height: 34px; letter-spacing: -0.4px; color: #151515; padding: 0 20px;\" valign=\"top\">"+
                "                                          Features."+
                "                                        </td>"+
                "                                      </tr>"+
                "                                      <tr>"+
                "                                        <td height=\"10\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                      </tr>"+
                "                                    </tbody>"+
                "                                    <tbody>"+
                "                                      <tr>"+
                "                                        <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 300; line-height: 28px; letter-spacing: -0.2px; color: #9B9B9B; padding: 0 20px;\" valign=\"top\">"+
                "                                          Co-ordinate campaigns and product launches, <br>with improved overall communication."+
                "                                        </td>"+
                "                                      </tr>"+
                "                                      <tr>"+
                "                                        <td height=\"20\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                      </tr>"+
                "                                    </tbody>"+
                "                                    <tbody>"+
                "                                      <tr>"+
                "                                        <td style=\"font-size: 0;\" valign=\"top\">"+
                "                                          <!--[if (gte mso 9)|(IE)]><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"><tr><td width=\"33%\" valign=\"top\"><![endif]-->"+
                "                                          <div class=\"pc-sm-mw-50pc pc-xs-mw-100pc\" style=\"display: inline-block; width: 100%; max-width: 186px; vertical-align: top;\">"+
                "                                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                              <tbody>"+
                "                                                <tr>"+
                "                                                  <td style=\"padding: 20px;\" valign=\"top\">"+
                "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td valign=\"top\">"+
                "                                                            <img src=\"cid:image2\" width=\"48\" height=\"48\" alt=\"\" style=\" max-width: 100%; height: auto; border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; display: block; color: #1B1B1B;\">"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"10\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 500; line-height: 24px; letter-spacing: -0.2px; color: #1B1B1B;\" valign=\"top\">"+
                "                                                            Responsive"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"6\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 300; line-height: 20px; letter-spacing: -0.2px; color: #9B9B9B;\" valign=\"top\">We ensure the pages work on any device.</td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                    </table>"+
                "                                                  </td>"+
                "                                                </tr>"+
                "                                              </tbody>"+
                "                                            </table>"+
                "                                          </div>"+
                "                                          <!--[if (gte mso 9)|(IE)]></td><td width=\"33%\" valign=\"top\"><![endif]-->"+
                "                                          <div class=\"pc-sm-mw-50pc pc-xs-mw-100pc\" style=\"display: inline-block; width: 100%; max-width: 186px; vertical-align: top;\">"+
                "                                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                              <tbody>"+
                "                                                <tr>"+
                "                                                  <td style=\"padding: 20px;\" valign=\"top\">"+
                "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td valign=\"top\">"+
                "                                                            <img src=\"cid:image3\" width=\"48\" height=\"48\" alt=\"\" style=\"max-width: 100%; height: auto; border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; display: block; color: #1B1B1B;\">"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"10\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 500; line-height: 24px; letter-spacing: -0.2px; color: #1B1B1B;\" valign=\"top\">"+
                "                                                            Clean code"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"6\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 300; line-height: 20px; letter-spacing: -0.2px; color: #9B9B9B;\" valign=\"top\">We create the highest quality code with zero bugs.</td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                    </table>"+
                "                                                  </td>"+
                "                                                </tr>"+
                "                                              </tbody>"+
                "                                            </table>"+
                "                                          </div>"+
                "                                          <!--[if (gte mso 9)|(IE)]></td><td width=\"33%\" valign=\"top\"><![endif]-->"+
                "                                          <div class=\"pc-sm-mw-50pc pc-xs-mw-100pc\" style=\"display: inline-block; width: 100%; max-width: 186px; vertical-align: top;\">"+
                "                                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                              <tbody>"+
                "                                                <tr>"+
                "                                                  <td style=\"padding: 20px;\" valign=\"top\">"+
                "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td valign=\"top\">"+
                "                                                            <img src=\"cid:image4\" width=\"48\" height=\"48\" alt=\"\" style=\"max-width: 100%; height: auto; border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; display: block; color: #1B1B1B;\">"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"10\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 500; line-height: 24px; letter-spacing: -0.2px; color: #1B1B1B;\" valign=\"top\">"+
                "                                                            Clean design"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"6\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 300; line-height: 20px; letter-spacing: -0.2px; color: #9B9B9B;\" valign=\"top\">Our design is at the highest quality, providing the user with the greatest experience.</td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                    </table>"+
                "                                                  </td>"+
                "                                                </tr>"+
                "                                              </tbody>"+
                "                                            </table>"+
                "                                          </div>"+
                "                                          <!--[if (gte mso 9)|(IE)]></td></tr></table><![endif]-->"+
                "                                        </td>"+
                "                                      </tr>"+
                "                                    </tbody>"+
                "                                  </table>"+
                "                                </td>"+
                "                              </tr>"+
                "                            </tbody>"+
                "                          </table>"+
                "                          <!-- END MODULE: Feature 1 -->"+
                "                          <!-- BEGIN MODULE: Footer 1 -->"+
                "                          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                            <tbody>"+
                "                              <tr>"+
                "                                <td class=\"pc-sm-p-21-10-14 pc-xs-p-5-0\" style=\"padding: 21px 20px 14px 20px; background-color: #1B1B1B\" valign=\"top\" bgcolor=\"#1B1B1B\" role=\"presentation\">"+
                "                                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                    <tbody>"+
                "                                      <tr>"+
                "                                        <td style=\"font-size: 0;\" valign=\"top\">"+
                "                                          <!--[if (gte mso 9)|(IE)]><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\"><tr><td width=\"280\" valign=\"top\"><![endif]-->"+
                "                                          <div class=\"pc-sm-mw-100pc\" style=\"display: inline-block; width: 100%; max-width: 280px; vertical-align: top;\">"+
                "                                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                              <tbody>"+
                "                                                <tr>"+
                "                                                  <td style=\"padding: 20px;\" valign=\"top\">"+
                "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 500; line-height: 24px; letter-spacing: -0.2px; color: #ffffff;\" valign=\"top\">"+
                "                                                            Follow Us."+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"11\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; letter-spacing: -0.2px; color: #D8D8D8;\" valign=\"top\">"+
                "                                                            We are always looking for new exciting projects and collaborations. Feel free to contact us."+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-sm-h-20\" height=\"56\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td style=\"font-family: Arial, sans-serif; font-size: 19px;\" valign=\"top\">"+
                "                                                            <a href=\"http://example.com\" style=\"text-decoration: none;\"><img src=\"cid:image5\" width=\"20\" height=\"20\" alt=\"\" style=\"border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; color: #ffffff;\"></a>"+
                "                                                            <span>  </span>"+
                "                                                            <a href=\"http://example.com\" style=\"text-decoration: none;\"><img src=\"cid:image6\" width=\"21\" height=\"18\" alt=\"\" style=\"border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; color: #ffffff;\"></a>"+
                "                                                            <span>  </span>"+
                "                                                            <a href=\"http://example.com\" style=\"text-decoration: none;\"><img src=\"cid:image7\" width=\"21\" height=\"20\" alt=\"\" style=\"border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; color: #ffffff;\"></a>"+
                "                                                            <span>  </span>"+
                "                                                            <a href=\"http://example.com\" style=\"text-decoration: none;\"><img src=\"cid:image8\" width=\"20\" height=\"20\" alt=\"\" style=\"border: 0; line-height: 100%; outline: 0; -ms-interpolation-mode: bicubic; color: #ffffff;\"></a>"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                    </table>"+
                "                                                  </td>"+
                "                                                </tr>"+
                "                                              </tbody>"+
                "                                            </table>"+
                "                                          </div>"+
                "                                          <!--[if (gte mso 9)|(IE)]></td><td width=\"280\" valign=\"top\"><![endif]-->"+
                "                                          <div class=\"pc-sm-mw-100pc\" style=\"display: inline-block; width: 100%; max-width: 280px; vertical-align: top;\">"+
                "                                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                              <tbody>"+
                "                                                <tr>"+
                "                                                  <td style=\"padding: 20px;\" valign=\"top\">"+
                "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\">"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 500; line-height: 24px; letter-spacing: -0.2px; color: #ffffff;\" valign=\"top\">"+
                "                                                            Contact us."+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"11\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; letter-spacing: -0.2px; color: #D8D8D8;\" valign=\"top\">400 Bizzell St, College Station, <br>TX 77843</td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-sm-h-20\" height=\"45\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 500; line-height: 24px; letter-spacing: -0.2px;\" valign=\"top\">"+
                "                                                            <a href=\"tel:123-456-7890\" style=\"text-decoration: none; color: #ffffff;\">123-456-7890</a>"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                        <tr>"+
                "                                                          <td height=\"9\" style=\"line-height: 1px; font-size: 1px;\"> </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                      <tbody>"+
                "                                                        <tr>"+
                "                                                          <td class=\"pc-fb-font\" style=\"font-family: 'Fira Sans', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 500; line-height: 24px;\" valign=\"top\">"+
                "                                                            <a href=\"mailto:tamu@tamu.edu\" style=\"text-decoration: none; color: #1595E7;\">tamutech@tamu.edu</a>"+
                "                                                          </td>"+
                "                                                        </tr>"+
                "                                                      </tbody>"+
                "                                                    </table>"+
                "                                                  </td>"+
                "                                                </tr>"+
                "                                              </tbody>"+
                "                                            </table>"+
                "                                          </div>"+
                "                                          <!--[if (gte mso 9)|(IE)]></td></tr></table><![endif]-->"+
                "                                        </td>"+
                "                                      </tr>"+
                "                                    </tbody>"+
                "                                  </table>"+
                "                                </td>"+
                "                              </tr>"+
                "                            </tbody>"+
                "                          </table>"+
                "                          <!-- END MODULE: Footer 1 -->"+
                "                        </td>"+
                "                      </tr>"+
                "                    </tbody>"+
                "                  </table>"+
                "                  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">"+
                "                    <tbody>"+
                "                      <tr>"+
                "                        <td height=\"20\" style=\"font-size: 1px; line-height: 1px;\"> </td>"+
                "                      </tr>"+
                "                    </tbody>"+
                "                  </table>"+
                "                </td>"+
                "              </tr>"+
                "            </tbody>"+
                "          </table>"+
                "          <!--[if (gte mso 9)|(IE)]></td></tr></table><![endif]-->"+
                "        </td>"+
                "      </tr>"+
                "    </tbody>"+
                "  </table>"+
                "  <!-- Fix for Gmail on iOS -->"+
                "  <div class=\"pc-gmail-fix\" style=\"white-space: nowrap; font: 15px courier; line-height: 0;\">                                                            </div>"+
                "</body>"+
                "</html>";
    }

}
