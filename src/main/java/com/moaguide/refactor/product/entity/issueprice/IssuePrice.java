package com.moaguide.refactor.product.entity.issueprice;

import com.moaguide.refactor.product.dto.IssueCustomDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "Issue_Price")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@SqlResultSetMapping(
	name = "IssueCustomDtoMapping",
	classes = @ConstructorResult(
		targetClass = IssueCustomDto.class,
		columns = {
			@ColumnResult(name = "productId", type = String.class),
			@ColumnResult(name = "name", type = String.class),
			@ColumnResult(name = "totalprice", type = Long.class),
			@ColumnResult(name = "day", type = Date.class),
			@ColumnResult(name = "category", type = String.class),
			@ColumnResult(name = "platform", type = String.class),
			@ColumnResult(name = "recruitmentRate", type = Double.class),
			@ColumnResult(name = "bookmark", type = Boolean.class)
		}
	)
)
public class IssuePrice {

	@EmbeddedId
	private IssuePriceId id;

	@Column(name = "day")
	private Date day;
}
