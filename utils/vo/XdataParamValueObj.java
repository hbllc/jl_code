package com.xgd.boss.core.vo;
/**
 * 大数据参数值对象
 * @author yanfuhua
 *
 */
public class XdataParamValueObj {
	/**
	 * 取反
	 */
	public static boolean OPR_NEGATION_TRUE = true;

	/** 值 */
	Object value;
	/** negation */
	boolean negation = false;

	public XdataParamValueObj(Object value, boolean negation) {
		super();
		this.value = value;
		this.negation = negation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isNegation() {
		return negation;
	}

	public void setNegation(boolean negation) {
		this.negation = negation;
	}

}
