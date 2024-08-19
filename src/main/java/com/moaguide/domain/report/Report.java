package com.moaguide.domain.report;

import com.moaguide.domain.summary.Summary;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Date date;
    private String category;
    @Column(name="sub_category")
    private String subCategory;
    private int view;

    public ReportCustomDto toCustomDto() {
        id = this.id;
        title = this.title;
        content = this.content;
        category = this.category;
        date = this.date;
        return new ReportCustomDto(id, title,content, category, date);
    }
}
