/**
 * 
 */
package com.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>Project: </p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015 .All Rights Reserved.</p>
 * @author <a href="yfh19880513@sina.com">yanfuhua</a>
 */
public class CollectionUtil {
	/**
	 * 将list按subListSize平均拆分成若干子集合
	 * @param list
	 * @param subListSize
	 * @return
	 */
	public static <T> List<List<T>> subSplit(Collection<T> list, int subListSize){
		if(list==null) return null;
		List<T> subList = new ArrayList<T>();
		List<List<T>> subListResult = new ArrayList<List<T>>();
		if(list.isEmpty()) return subListResult;
		Iterator<T> it = list.iterator();
		int i = 1;
		while(it.hasNext()){
			subList.add(it.next());
			if(i>1 && i%subListSize==0){
				subListResult.add(subList);
				subList = new ArrayList<T>();
			}			
			i++;
		}
		if(CommonUtil.isNotEmpty(subList)){
			subListResult.add(subList);
		}
		return subListResult;		
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for(int i=0;i<10;i++){
			list.add(""+i);
		}
		List<List<String>> subLists =  CollectionUtil.subSplit(list, 2);
		for(List<String> subL: subLists){
			System.out.println(subL);
		}
	}
}	
