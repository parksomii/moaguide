package com.moaguide.domain.report;

import com.moaguide.domain.summary.Summary;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    private Summary productId;
    private Date date;
}
