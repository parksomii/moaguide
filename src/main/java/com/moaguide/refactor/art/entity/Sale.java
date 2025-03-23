package com.moaguide.refactor.art.entity;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.customDto.finishCustomDto;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Sale")
@SqlResultSetMapping(
	name = "SaleCustomDtoMapping",
	classes = @ConstructorResult(
		targetClass = finishCustomDto.class,
		columns = {
			@ColumnResult(name = "productId", type = String.class),
			@ColumnResult(name = "name", type = String.class),
			@ColumnResult(name = "category", type = String.class),
			@ColumnResult(name = "platform", type = String.class),
			@ColumnResult(name = "sailRate", type = Double.class),
			@ColumnResult(name = "bookmark", type = Boolean.class),
			@ColumnResult(name = "sale", type = Boolean.class)
		}
	)
)
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
	private Product productId;

	@Column(name = "sale_price", precision = 15, scale = 2)
	private BigDecimal salePrice; // 매각가 (소수점 2자리 포함)

	@Column(name = "sale_date")
	private LocalDate saleDate; // 매각일 (날짜)

	@Column(name = "sale_place", length = 100)
	private String salePlace; // 매각 형태

	@Column(name = "sale_rate")
	private Double saleRate; // 매각 비율
}
