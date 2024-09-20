package com.moaguide.domain.detail;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.ContentDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "content_Detail")
@AllArgsConstructor
@NoArgsConstructor
@NamedStoredProcedureQuery(
        name = "ContentDetailProcedure", // 프로시저 호출 이름
        procedureName = "GetContentDetails", // 실제 프로시저 이름
        resultSetMappings = "ContentDetailDtoMapping", // 결과 매핑
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Id", type = String.class) // IN 파라미터 정의
        }
)
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
                        @ColumnResult(name = "lowPrice", type = Integer.class)
                }
        )
)
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
