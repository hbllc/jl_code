package com.xgd.boss.core.thrift;

import com.jlpay.commons.command.CommandRequest;
import com.jlpay.commons.command.CommandResponse;
import com.jlpay.commons.rpc.thrift.referer.service.RpcAdapterService;
import com.xgd.boss.core.thrift.req.CommonThriftReq;
import com.xgd.boss.core.utils.JsonUtil;
import com.xgd.boss.core.utils.SpringContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;

import java.lang.reflect.Type;

/**
 * thrift统一出口
 * @author chenkai
 *
 */
public class ThriftClientUtil {
	
	private static final Log logger = LogFactory.getLog(ThriftClientUtil.class);
	
	/**
	 * @param serverName
	 * @param commandRequest
	 * @param logid
	 * @param responseClass
	 * @return
	 * @throws Exception
	 */
	public static <T> T invoke(String serverName,CommandRequest commandRequest,String logid,Class<? extends CommandResponse> responseClass,Type type) throws Exception{
		RpcAdapterService rpcAdapterService = SpringContext.getBean(RpcAdapterService.class);
		CommandResponse resp = rpcAdapterService.invoke(serverName,commandRequest,logid,responseClass);
		return JsonUtil.fromJsonO(JsonUtil.toJson(resp), type);
	}
	
	/**
	 * 交易提现记录
	 * @param client
	 * @param param
	 * @param type
	 * @return
	 * @throws TException
	 */
	@Deprecated
	public static <T> T invoke(SystemCommand.Iface client,CommonThriftReq param,Type type) throws TException{
		String responseStr = client.invoke(param.getLogid(), JsonUtil.toJson(param));
		logger.info("交易提现记录："+responseStr);
		return JsonUtil.fromJsonO(responseStr, type);
	}
}
