package com.moaguide.domain.elasticsearch.music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "music")
public class Music {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String song;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String singer;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String lyricist;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String composer;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String arranger;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String description;

    @Field(type = FieldType.Text, index = false)
    private String productId;

    // Getters and Setters
}
