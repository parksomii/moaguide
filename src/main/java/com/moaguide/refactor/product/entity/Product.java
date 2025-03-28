package com.moaguide.refactor.product.entity;

import com.moaguide.refactor.product.dto.SummaryCustomDto;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
@Entity
@Setter
@SqlResultSetMapping(
	name = "SummaryCustomDtoMapping",
	classes = @ConstructorResult(
		targetClass = SummaryCustomDto.class,
		columns = {
			@ColumnResult(name = "product_Id", type = String.class),
			@ColumnResult(name = "name", type = String.class),
			@ColumnResult(name = "totalprice", type = Long.class),
			@ColumnResult(name = "price", type = Integer.class),
			@ColumnResult(name = "priceRate", type = Double.class),
			@ColumnResult(name = "lastDivide_rate", type = Double.class),
			@ColumnResult(name = "category", type = String.class),
			@ColumnResult(name = "platform", type = String.class),
			@ColumnResult(name = "bookmark", type = Boolean.class),
			@ColumnResult(name = "sale", type = Boolean.class)
		}
	)
)
public class Product {

	@Id
	@Column(name = "Product_Id")
	private String productId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Platform_Id", referencedColumnName = "Platform_Id")
	private Platform PlatformId;

	private String name;

	private Integer piece;

	private Integer views;

	@Column(name = "now_piece")
	private Integer nowPiece;

	private String link;

	public String getCategory() {
		return PlatformId.getCategory();
	}

	public String getPlatform() {
		return PlatformId.getPlatform();
	}

}
