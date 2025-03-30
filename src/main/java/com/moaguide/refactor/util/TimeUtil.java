package com.moaguide.refactor.util;

import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;


public class TimeUtil {
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static DateTime getNowDateTime() {
		return new DateTime(System.currentTimeMillis());
	}

	public static LocalDate getMinusLocalDate(int month) {
		//LocalDate localDate = LocalDate.now().minusYears(1);
		return LocalDate.now().minusYears(month);
	}
}
