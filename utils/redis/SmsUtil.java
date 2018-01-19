package com.xgd.boss.core.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xgd.boss.core.sms.HttpRequest;
import com.xgd.boss.core.utils.JsonUtil;

/**
 * 短信发送类
 * @author pengdanhua
 *
 */
@Component
public class SmsUtil {
  private  final static Log log = LogFactory.getLog(SmsUtil.class);
  
  @Value("${sms.url}")
  private String SMS_URL;
  @Value("${sms.msgid:10023}")
  private String MSG_ID;
  @Value("${sms.orgId:10023}")
  private String ORG_ID;
  @Value("${sms.sendUserId:10023}")
  private String SEND_USER_ID;
  @Value("${sms.priority:9}")
  private String PRIORITY;
  @Value("${sms.isneedreport:0}")
  private String IS_NEED_REPORT;
  
  /**
   * 
   * @param key - 短信的key，可以随便定义个不重复的 eg：com.xgd.membership.sms.machine.remove
   * @param phoneNo - 目的号码，支持多个用户号码，用“,”号隔开
   * @param smsId - 字段时用户端生成插入，（最好不要重复）
   * @param smsSign - 短信签名，到手机上显示在短信内容最后面
   * @param msg - 短信内容
   * @return 
   * @throws Exception 
   */
  public String send(String phoneNo,String msg) throws Exception{
    Map<String, String> map = new HashMap<String, String>();
    map.put("destAddr", phoneNo); //目的号码，支持多个用户号码，用“,”号隔开
    map.put("smsSign", ""); //短信签名，到手机上显示在短信内容最后面
    map.put("msg", msg); //短信内容
    
    map.put("smsId", MSG_ID); //字段时用户端生成插入，（最好不要重复）
    map.put("isneedreport", IS_NEED_REPORT); //若用户需要状态报告，就设置1 ，若不需要: 设置0
    map.put("orgId", ORG_ID); //用户组ID或所在机构id或相关的信息ID,此处需要指定有意义的值
    map.put("sendUserId", SEND_USER_ID); //用户ID, 如果需要分用户统计，此处需要指定有意义的值
    map.put("priority", PRIORITY); //指定本条短信的优先级（0-9），数值越大，优先级越高
    map.put("channel", ""); 
    map.put("presendTime", "");
    map.put("validTime", "");
    map.put("extCode", "");
    
    Map<String, Object> datamap = new HashMap<String, Object>();
    datamap.put("data", map);
    datamap.put("key", "com.xgd.smsmt.create");
    
    if(log.isDebugEnabled()) log.debug("sms param:"+JsonUtil.toJson(map));
    if(log.isDebugEnabled()) log.debug("sms url:"+SMS_URL);
//    String retS = HttpUtil.httpPost(SMS_URL, map);
    String retS = HttpRequest.sendPost(SMS_URL, "json="+ JsonUtil.toJson(datamap));
    if(log.isDebugEnabled()) log.info("sms response:"+JsonUtil.toJson(retS));
    return retS;
  }
  
  public boolean isEnabled() {
	  return null!=SMS_URL && !SMS_URL.isEmpty();
  }
  
}
