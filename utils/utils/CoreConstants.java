package com.xgd.boss.core.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 全局常量
 * @author chenqiguo
 *
 */
public class CoreConstants {

	/**
	 * API返回码成功
	 * */
	public static final String API_CODE_SUCCESS="0";
	
	/**
	 * API返回码失败
	 * */
	public static final String API_CODE_ERROR="1";
	
	public static final String BANK_TYPE_PUBLIC = "1" ;//银行对公
	public static final String BANK_TYPE_PRIVATE = "0";//对私
//	
//	/**
//	 * 营业执照入网
//	 */
//	public static final String NET_TYPE_CHARTER = "1";//營業執照入网
//	/**
//	 * 租赁合同入网
//	 */
//	public static final String NET_TYPE_CONTRACT = "2";//租赁合同入网
	
	/**
	 * API返回码参数有误
	 * */
	public static final String API_CODE_INVALIDPARAMS="100";
	
	//public static String PARAMS_F42_API="merchantname|b,simplename|email,enmerchantname,settle_card,auth_settle_card,cycle_settle_trade,cycle_settle_fee,fee_package_id,business_open|date,business_close,can_preauth,busniess_tags,mcc|n4-4,region_code|n6-6,limit,org_id,agent_slot,jl_slot,standard_fee|N1-1";
	
	public static Map<String,String> paramsConfigMap=new HashMap<String,String>();
	public static Map<String,Map<String,String[]>> rulesMap;
	public static void initparamsConfigMap(){
		paramsConfigMap.put("api.f42", "merchantname|b,simplename,enmerchantname,settle_card,auth_settle_card,cycle_settle_trade,cycle_settle_fee,fee_package_id,business_open|date,business_close,can_preauth,busniess_tags,mcc|n4-4,region_code|n6-6,limit,org_id,agent_slot,jl_slot,standard_fee|N1-1");
		paramsConfigMap.put("api.customerregister", "cert_type|s2,cert_no,customer_role,parent_id|b,login_no|b,login_phone|b|mobile,login_email|b|email,pwd,name,simple_name|b,english_name|b,address|b,attatch|b");
		paramsConfigMap.put("api.modifyCustomer", "id,status|s1-1");
		paramsConfigMap.put("api.queryAgent", "pageNum|n1000,numPerPage|n2");
		paramsConfigMap.put("api.queryBusinessman", "customer_id,pageNum|n1000,numPerPage|n2");
		paramsConfigMap.put("api.queryF42Nums", "customer_id,startTime|date,endTime|date,pageNum|n1000,numPerPage|n2");
		paramsConfigMap.put("api.customerinfo", "customer_id,license_no,c_tax,d_tax,org_license,cerman_license,cername,business_scope|s400,enterpriseproperty,annexpath,begindate|date,enddate|date,ceraddress|b,licenseaddress,linktel|tel,business_hours_begin,business_hours_end,info_json|s1000	");
		paramsConfigMap.put("api.merlease", "f42_id,customer_id,leasecontract_code,lessor_type|s1-1,lessor_corpname,lessor_name,lessor_certtype|s2-2,lessor_idcard,lessor_tel|tel,lessor_housecode,lessee_mername,lessee_name,lessee_certtype|s2-2,lessee_idcard,lessee_tel|tel,lessee_addr,lesse_startdate|date,lesse_enddate|date,lesse_space,monthly_rent|n10,business_scope|s400,business_hours_begin,business_hours_end,ceraddress|s200,annexpath|s250");
		paramsConfigMap.put("api.modifyF42", "f42_id,status|s1-1");
		paramsConfigMap.put("api.modifyF42", "acustomer_id,bcustomer_id,tags_system|s1-1,tags_self|b");
		paramsConfigMap.put("api.f41", "f42_id,f42|n15-15,fp41|b,fp43|b,provideform|n1-1,region_code|n6-6,address|b");
		paramsConfigMap.put("api.customercard", "customer_id,cardno,business_tags|n1-1,card_type|n1-1,name|b,cert_type,cert_no,mobile|mobile,bank_no,branch_bank_no|n12-12");
	
		paramsConfigMap.put("api.terminalActivate", "merchantNo|n15,terminalNo|n8,deviceSn,code|s2,desc|b");
		
	}
	public static void initRulesMap(){
		Iterator<String> it=paramsConfigMap.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			String value=paramsConfigMap.get(key);
			rulesMap.put(key, parseParamsWithValidate(value));
		}
	}
	public static Map<String,Map<String,String[]>> getAllRulesMap(){
		if(rulesMap==null){
			rulesMap=new HashMap<String,Map<String,String[]>>();
			initparamsConfigMap();
			initRulesMap();
		}
		return rulesMap;
	}
	
	 /**
     * 根据参数串解出参数及验证规则
     * */
    public static Map<String,String[]> parseParamsWithValidate(String paramsStr){
    	if(StringUtils.isBlank(paramsStr)){
    		return null;
    	}
    	Map<String ,String[]> map=new LinkedHashMap<String,String[]>();
    	String[] strs=paramsStr.split(",");
    	for(String str:strs){
    		str=str.trim();
    		String[] params=str.split("\\|");
    		String[] rules=null;
    		if(params.length>1){
    			rules=new String[params.length-1];
    			System.arraycopy(params, 1, rules, 0, params.length-1);
    		}
    		map.put(params[0], rules);
    	}
    	
    			
    	return map;
    }
}

