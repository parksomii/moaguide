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

    private Date timestamp;

    public SearchLog(String searchTerm, Date timestamp) {
        this.searchTerm = searchTerm;
        this.timestamp = timestamp;
    }

}
