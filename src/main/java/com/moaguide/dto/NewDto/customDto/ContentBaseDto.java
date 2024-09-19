package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentBaseDto {
    private ContentPublishDto publish;
    private ContentInvestmentDto investment;

    public ContentBaseDto(ContentPublishDto publish) {
        this.publish = publish;
        this.investment = null;
    }
}
