package com.moaguide.domain.issueprice;

import com.moaguide.dto.NewDto.customDto.IssueCustomDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "Issue_price")
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
                        @ColumnResult(name = "recruitmentRate", type = Double.class)
                }
        )
)
@NamedStoredProcedureQuery(
        name = "findIssueList",
        procedureName = "Issue",
        resultSetMappings = "IssueCustomDtoMapping",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "page", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "size", type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "day", type = Date.class)
        }
)
@NamedStoredProcedureQuery(
        name = "findstartList",
        procedureName = "start",
        resultSetMappings = "IssueCustomDtoMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "page", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "size", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "day", type = Date.class)
        }
)
public class IssuePrice {

    @EmbeddedId
    private IssuePriceId id;

    @Column(name = "day")
    private Date day;
}
