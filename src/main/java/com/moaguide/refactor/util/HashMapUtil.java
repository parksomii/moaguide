package com.moaguide.refactor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashMapUtil {

	public static Map<String,Object> createEmptyHashMap(String name){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name",new ArrayList<>());
		return map;
	}

}
