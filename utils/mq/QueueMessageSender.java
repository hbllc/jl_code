package com.xgd.boss.core.mq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @Description mq发送信息--xml配置实例化
 *
 */
public class QueueMessageSender {
	private static final Log logger = LogFactory.getLog(QueueMessageSender.class);
	
	private JmsTemplate jmsTemplate;
	

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}


	/**
	 * 向指定队列发送文本消息
	 */
	public void sendMessage(Destination destination, final String msg) {
		logger.info("=======send message to :" + destination.toString() + ",messge:" + msg);
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	/**
	 * 向默认队列发送文本消息
	 */
	public void sendMessage(final String msg) {
		logger.info("=======send message to default mq, messge:" + msg);
		//不要用send(String,...)方法,调用Destination的toString会在队列名前加上queue://
		jmsTemplate.send(jmsTemplate.getDefaultDestination(), new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}



}
