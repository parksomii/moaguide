package com.moaguide.refactor.art.entity;

import com.moaguide.refactor.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Art_Author")
public class ArtAuthor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
	private Product productId;

	@Column(name = "author_name", nullable = false, length = 255)
	private String authorName; // 작가 이름

	@Column(name = "nationality", length = 100)
	private String nationality; // 국적

	@Column(name = "birth")
	private LocalDate birth; // 태생년도

	@Column(name = "academic_ability", columnDefinition = "TEXT")
	private String academicAbility; // 학력

	@Column(name = "award_career", columnDefinition = "TEXT")
	private String awardCareer; // 수상 경력

	@Column(name = "major", columnDefinition = "TEXT")
	private String major; // 대표작

	@Column(name = "annual_sale", length = 255)
	private String annualSale; // 연도별 판매금액

	@Column(name = "introduction", columnDefinition = "TEXT")
	private String introduction; // 작가활동 및 소개
}
