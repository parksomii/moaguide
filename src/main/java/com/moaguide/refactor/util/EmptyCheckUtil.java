package com.moaguide.refactor.util;


import java.util.List;

public class EmptyCheckUtil {

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}else{
			return false;
		}
	}

	public static boolean isListEmpty(List<?> list) {
		if (list == null) {
			return true;
		}else {
			return false;
		}
	}
}
