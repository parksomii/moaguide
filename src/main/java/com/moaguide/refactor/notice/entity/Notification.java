package com.moaguide.refactor.notice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {
    @Id
    private Long id;
    private String nickName;
    private String link;
    private String message;
    private LocalDate Date;

    public Notification(String nickName, String link, String message, LocalDate Date) {
        this.nickName = nickName;
        this.link = link;
        this.message = message;
        this.Date = Date;
    }
}
