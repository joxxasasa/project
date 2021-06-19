package com.iktpreobuka.project.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.models.EmailObject;

@Service
public class EmailServiceImpl implements EmailService {
	
	
	@Autowired
	JavaMailSender emailSender;
	
	@Autowired
	public VoucherDAO voucherDAO;
	
//	@Override
//	public void sendVoucher(EmailObject emailObject) throws
//	Exception {
//		MimeMessage mail = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new
//		MimeMessageHelper(mail, true);
//		helper.setTo(emailObject.getTo());
//		helper.setSubject(emailObject.getSubject());
//		String text = "<html><body><table"
//		+ "style='border:2px solid black'>"
//		+ "<tr><td>" + emailObject.getText() + "</td></tr>"
//		+ "</table></body></html>";
//		helper.setText(text, true);
//		emailSender.send(mail);
//
//	}
	
	@Override
	public void sendVoucher(EmailObject emailObject) throws
	Exception {
		MimeMessage mail = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(emailObject.getTo());
		helper.setSubject(emailObject.getSubject());
		String text = "<html>"
				+"<head><style>table, th, td {border: 1px solid black;} th { text-align: center;} </style>"
				+ "<body><H2>VOUCHER</h2>"
				+ "<table style='width: 70%'>"
				+ "<tr><th>Buyer</th>"
			    + "		<th>Offer</th>"
			    + "		<th>Price</th>"
			    + "		<th>Expires date</th></tr>"
			    + "<tr><td style=\"text-align:center\">Jill</td>"
			    + "    <td style=\"text-align:center\">Smith</td>"
			    + "    <td style=\"text-align:center\">50</td>"
			    + "    <td style=\"text-align:center\">50</td></tr>"
			    + "</table>"
			    + "</body>"
			    + "</html>";
		helper.setText(text, true);
		emailSender.send(mail);

	}
}
