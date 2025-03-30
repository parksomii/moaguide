package com.moaguide.refactor.util;



public class EmptyCheckUtil {

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}else{
			return false;
		}
	}

	public static boolean isListEmpty(Object[] list) {
		if (list == null || list.length == 0) {
			return true;
		}else {
			return false;
		}
	}
}
