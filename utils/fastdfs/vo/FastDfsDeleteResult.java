package com.core.fastdfs.vo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * FastDfs删除结果
 * @author huangweiqi
 * 2015-6-27
 */
public class FastDfsDeleteResult implements Serializable {

	private static final long serialVersionUID = 4855215232384411275L;

	/**
	 * 删除结果，全部成功
	 */
	public final static String DELETE_RESULT_ALL_SUCCESS = "1";
	/**
	 * 删除结果，部分成功
	 */
	public final static String DELETE_RESULT_PARTIAL_SUCCESS = "2";
	/**
	 * 删除结果，全部失败
	 */
	public final static String DELETE_RESULT_ALL_ERROR = "3";
	
	
	/**
	 * 成功的FastDfs路径list
	 */
	private List<String> successList = Collections.emptyList();
	/**
	 * 失败的FastDfs路径List
	 */
	private List<String> errorList = Collections.emptyList();
	/**
	 * 结果
	 */
	private String result = DELETE_RESULT_ALL_ERROR;

	public List<String> getSuccessList() {
		return successList;
	}

	public void setSuccessList(List<String> successList) {
		this.successList = successList;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
