package com.moaguide.refactor.contents.dto;


import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BroadcastInfoDto {

	private Date airDate;
	private String director;
	private String cast;
	private String company;
	private String channel;
}
