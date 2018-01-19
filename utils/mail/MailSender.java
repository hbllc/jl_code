package com.xgd.boss.core.mail;

import java.util.Arrays;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class MailSender {
	private static final Log logger = LogFactory.getLog(MailSender.class);
	@Autowired(required=false)
	private JavaMailSender javaMailSender;
	
	public void send(Message message) throws Exception{
		try {
			MimeMessage mime = javaMailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");
			helper.setFrom(message.getFrom());
			helper.setTo(message.getTo());
			helper.setSubject(message.getSubject());
			helper.setText(message.getContent());
			if(message.getAttachments()!=null){
				for(Attachment att : message.getAttachments()){
					helper.addAttachment(MimeUtility.encodeWord(att.getFileName()), att.getFile());
				}
			}
			logger.info("begin to send mail...");
			javaMailSender.send(mime);
			logger.info("mail sent to:"+Arrays.asList(message.getTo()));
		} catch (Exception e) {
			logger.error(e, e);
			throw e;
		}
	}
}
