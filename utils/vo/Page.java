package com.xgd.boss.core.vo;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Page<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DEFAUL_PAGESIZE = 20;

	private int total = 0;
	private int pageNum = 1;
	private int numPerPage = DEFAUL_PAGESIZE;
	private Collection<T> list;
	private boolean startAndEnd = true;
	public Page(){
		
	}
	public Page(int total, int pageNum, int pageSize, Collection<T> list) {
		super();
		this.total = total;
		this.pageNum = pageNum;
		this.numPerPage = pageSize;
		this.list = list;
	}
	public int getPageCount(){
		return (int) Math.ceil((double)total/numPerPage);
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public Collection<T> getList() {
		return list;
	}
	public void setList(Collection<T> list) {
		this.list = list;
	}
	public boolean getStartAndEnd() {
		return startAndEnd;
	}
	public void setStartAndEnd(boolean startAndEnd) {
		this.startAndEnd = startAndEnd;
	}
	/**
	 * 分页的开始页
	 */
	public final Integer getStart(){
		if(startAndEnd)
			return (this.pageNum-1)*this.numPerPage;
		else
			return null;
	}
	/**
	 * 分页的大小
	 */
	public final Integer getEnd(){
		if(startAndEnd)
			return this.pageNum*this.numPerPage;
		else
			return null;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
