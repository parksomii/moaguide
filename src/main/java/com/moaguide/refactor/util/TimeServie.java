package com.moaguide.refactor.util;

import java.sql.Timestamp;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@NoArgsConstructor
public class TimeServie {
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static DateTime getNowDateTime() {
		return new DateTime(System.currentTimeMillis());
	}
}
