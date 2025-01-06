package com.moaguide.dto.NewDto.customDto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private String link;
    private String message;
    private LocalDate Date;
}
