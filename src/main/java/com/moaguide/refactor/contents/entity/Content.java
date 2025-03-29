package com.moaguide.refactor.contents.entity;

import com.moaguide.refactor.contents.dto.ContentDetailDto;
import com.moaguide.refactor.contents.dto.ContentPublishDto;
import com.moaguide.refactor.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "content_Detail")
@AllArgsConstructor
@NoArgsConstructor

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "ContentDetailProcedure", // 첫 번째 프로시저 호출 이름
		procedureName = "GetContentDetails", // 실제 첫 번째 프로시저 이름
		resultSetMappings = "ContentDetailDtoMapping", // 첫 번째 프로시저 결과 매핑
		parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Id", type = String.class),
			// 첫 번째 프로시저 IN 파라미터 정의
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "nickname", type = String.class)
		}
	),
	@NamedStoredProcedureQuery(
		name = "ContentPublishProcedure", // 두 번째 프로시저 호출 이름
		procedureName = "GetContentpublish", // 실제 두 번째 프로시저 이름
		resultSetMappings = "ContentPublishMapping", // 두 번째 프로시저 결과 매핑
		parameters = {
			@StoredProcedureParameter(mode = ParameterMode.IN, name = "pro_Id", type = String.class)
			// 두 번째 프로시저 IN 파라미터 정의
		}
	)
})
@SqlResultSetMappings({
	@SqlResultSetMapping(
		name = "ContentDetailDtoMapping",
		classes = @ConstructorResult(
			targetClass = ContentDetailDto.class,
			columns = {
				@ColumnResult(name = "productId", type = String.class),
				@ColumnResult(name = "name", type = String.class),
				@ColumnResult(name = "genre", type = String.class),
				@ColumnResult(name = "category", type = String.class),
				@ColumnResult(name = "platform", type = String.class),
				@ColumnResult(name = "totalPrice", type = long.class),
				@ColumnResult(name = "rate", type = Double.class),
				@ColumnResult(name = "date", type = Date.class),
				@ColumnResult(name = "lowPrice", type = Integer.class),
				@ColumnResult(name = "link", type = String.class),
				@ColumnResult(name = "bookmark", type = Boolean.class)
			}
		)
	),
	@SqlResultSetMapping(
		name = "ContentPublishMapping",
		classes = @ConstructorResult(
			targetClass = ContentPublishDto.class,
			columns = {
				@ColumnResult(name = "name", type = String.class),
				@ColumnResult(name = "genre", type = String.class),
				@ColumnResult(name = "type", type = String.class),
				@ColumnResult(name = "minAmount", type = BigInteger.class),
				@ColumnResult(name = "maxAmount", type = BigInteger.class),
				@ColumnResult(name = "piece", type = Integer.class),
				@ColumnResult(name = "basePrice", type = Integer.class),
				@ColumnResult(name = "minInvestment", type = Integer.class),
				@ColumnResult(name = "issuanceDate", type = Date.class),
				@ColumnResult(name = "expirationDate", type = Date.class)
			}
		)
	)
})
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
	private Product productId;

	@Column(name = "genre")
	private String genre;

	@Column(name = "stock_Type")
	private String stockType;

	@Column(name = "min_Amount")
	private Long minAmount;

	@Column(name = "max_Amount")
	private Long maxAmount;

	@Column(name = "min_Investment")
	private Integer minInvestment;

	@Column(name = "Issue_Date")
	private Date issueDate;

	@Column(name = "expiration_Date")
	private Date expirationDate;

	@Column(name = "end_Date")
	private Date endDate;

	@Column(name = "total_Budget")
	private Long totalBudget;

	@Column(name = "customer_Unit_Price")
	private Integer customerUnitPrice;

	@Column(name = "profit_Loss_Ratio")
	private String profitLossRatio;

	@Column(name = "break_Even_Point")
	private String breakEvenPoint;

	@Column(name = "base_rate")
	private String baseRate;

	@Column(name = "max_rate")
	private String maxRate;

}
