package com.xgd.boss.core.sms;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xgd.boss.core.mq.QueueMessageSender;
import com.xgd.boss.core.utils.JsonUtil;
import com.xgd.boss.core.utils.SpringContext;
import com.xgd.boss.core.vo.LsmsVo;

/**
 * @author chenkai
 *
 */
public class LSmsUtil {
	
	private static final Log logger = LogFactory.getLog(LSmsUtil.class);
	/**
	 * 
	 * @param smsVo
	 */
	public static void sendSms(LsmsVo smsVo){
		logger.info("发送至短信mq:"+JsonUtil.toJson(smsVo));
		QueueMessageSender sender = SpringContext.getBean(QueueMessageSender.class);
		if(null==sender){
			throw new NullPointerException("msg sender is null");
		}else{
			sender.sendMessage(JsonUtil.toJson(smsVo));
		}
	}
	
	/**
	 * @param content
	 * @param mobiles
	 * @throws SmsException
	 */
	public static void sendSms(String content,List<String> mobiles){
		logger.info("发送至短信mq:"+JsonUtil.toJson(mobiles)+"content:"+content);
		if(null!=mobiles&&mobiles.size()>0){
			for(String m:mobiles){
				LsmsVo smsVo = new LsmsVo();
				smsVo.setMobile(m);
				smsVo.setMsg(content);
				try {
					sendSms(smsVo);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}else{
			logger.info("短信手机号不能为空");
		}
	}

}
