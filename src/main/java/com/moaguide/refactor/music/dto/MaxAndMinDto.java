package com.moaguide.refactor.music.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MaxAndMinDto {

	private List<SteamingDto> steamingDto;
	private int max;
	private int min;
}
