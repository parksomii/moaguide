package com.moaguide.domain.Announcement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Announcement {
    @Id
    private Long id;
    private String title;
    private LocalDate date;
    private String content;
}
