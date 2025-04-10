package com.moaguide.refactor.search.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Profile({"blue", "green"})
public class ProductsEntity {

    @Id
    private String productId;

    private String name;
    private String category;
    private String kr_category ;
    private String platform;
    private String address;
    private String director;
    private String cast;
    private String writer;
    private String singer;
    private String lyricist;
    private String composer;
    private String arranger;
    private String farm;
    private String introduction;

}