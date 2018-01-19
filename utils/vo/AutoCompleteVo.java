package com.xgd.boss.core.vo;

import java.io.Serializable;

/**
 * 前端自动完成vo<br />
 * 前端的自动完成功能，需要返回给前段[{"code":"", text""}]这样结构的数据
 */
public class AutoCompleteVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 自动完成-code
     */
    private String code;

    /**
     * 自动完成-描述
     */
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AutoCompleteVo{");
        sb.append("code='").append(code).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
