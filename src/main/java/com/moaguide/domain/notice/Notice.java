package com.moaguide.domain.notice;

import com.moaguide.domain.summary.Summary;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Summary productId;
    private String title;
    private String content;
    private LocalDate date;

}
