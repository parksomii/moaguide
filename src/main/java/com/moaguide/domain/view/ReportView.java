package com.moaguide.domain.view;

import com.moaguide.domain.report.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Report_View")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportView {
    @EmbeddedId
    private ReportViewId id;

}
