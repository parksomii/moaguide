package com.moaguide.domain.pdf;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pdf_list")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PdfList {

    @Id
    private String Id;

    private String name;
}
