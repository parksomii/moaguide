package com.moaguide.domain.elasticsearch.searchlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "search-logs")
public class SearchLog {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String searchTerm;

    @Field(type = FieldType.Date)
    private ZonedDateTime timestamp;

    public SearchLog(String searchTerm, ZonedDateTime timestamp) {
        this.searchTerm = searchTerm;
        this.timestamp = timestamp;
    }

    // 현재 UTC 시간을 사용해 생성하는 메서드
    public static SearchLog createWithCurrentTimestamp(String searchTerm) {
        ZonedDateTime nowUtc = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);
        return new SearchLog(searchTerm, nowUtc);
    }
}
