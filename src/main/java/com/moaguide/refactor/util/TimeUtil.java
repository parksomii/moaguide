package com.moaguide.refactor.util;

import java.sql.Timestamp;
import org.joda.time.DateTime;



public class TimeUtil {
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static DateTime getNowDateTime() {
		return new DateTime(System.currentTimeMillis());
	}
}
