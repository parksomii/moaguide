package com.moaguide.domain.detail;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "BankcowDetail")
public class HanwooDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

    @Column(name = "type", length = 30)
    private String type; // 증권 종류

    @Column(name = "issuer", length = 30)
    private String issuer; // 발행인

    @Column(name = "recruitingType", length = 50)
    private String recruitingType; // 모집방법

    @Column(name = "rightsStructure", length = 50)
    private String rightsStructure; // 권리구조

    @Column(name = "revenueStructure", length = 50)
    private String revenueStructure; // 수익구조

    @Column(name = "paymentDate")
    private LocalDate paymentDate; // 납입기일

    @Column(name = "subscriptionDate")
    private LocalDate subscriptionDate; // 청약공고일

    @Column(name = "allocationDate")
    private LocalDate allocationDate; // 배정공고일

    @Column(name = "criteriaDate")
    private LocalDate criteriaDate; // 배정기준일

    @Column(name = "certificationNumber", length = 50)
    private String certificationNumber; // 인증번호

    @Column(name = "certificationAgency", length = 50)
    private String certificationAgency; // 인증기관

    @Column(name = "manager", length = 30)
    private String manager; // 관리책임자

    @Column(name = "certifiedHeads")
    private String certifiedHeads; // 인증두수

    @Column(name = "cattleBreed", length = 50)
    private String cattleBreed; // 축종

    @Column(name = "initialDate")
    private LocalDate initialDate; // 최초인증일
}
