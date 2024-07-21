package com.moaguide.dto;

import com.moaguide.domain.summary.Summary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicDetailDto {

    private Integer id;

    private Summary productId;

    private String introduceSong;

    private String genre;

    private String singer;

    private String writer;

    private String composing;

    private String arrangements;

    private Date announcementDate;

    private Integer issuePrice;

    private Date issuanceDate;

    private String type;

    private String youtubeUrl;

    private String keyword;

    public MusicDetailDto(Summary productId, String introduceSong, String genre, String singer,
                          String writer, String composing, String arrangements, Date announcementDate,
                          Integer issuePrice, Date issuanceDate, String type,String youtubeUrl, String keyword) {
        this.productId = productId;
        this.introduceSong = introduceSong;
        this.genre = genre;
        this.singer = singer;
        this.writer = writer;
        this.composing = composing;
        this.arrangements = arrangements;
        this.announcementDate = announcementDate;
        this.issuePrice = issuePrice;
        this.issuanceDate = issuanceDate;
        this.type = type;
        this.youtubeUrl=youtubeUrl;
        this.keyword=keyword;
    }
}
