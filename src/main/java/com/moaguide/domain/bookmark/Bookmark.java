package com.moaguide.domain.bookmark;

import com.moaguide.domain.product.Product;
import com.moaguide.domain.user.User;
import com.moaguide.dto.NewDto.customDto.BookmarkProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SqlResultSetMapping(
        name = "BookmarkPRoduct",
        classes = @ConstructorResult(
                targetClass = BookmarkProductDto.class,
                columns = {
                        @ColumnResult(name = "product_Id", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "category", type = String.class),
                        @ColumnResult(name = "platform", type = String.class)
                }
        )
)
@NamedStoredProcedureQuery(
        name = "bookmarkProductProcedure",
        procedureName = "bookmark_product",
        resultSetMappings = "BookmarkPRoduct",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "page", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "size", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "nickname", type = String.class)
        }
)
@NamedStoredProcedureQuery(
        name = "bookmarkProductCategoryProcedure",
        procedureName = "bookmark_product_category",
        resultSetMappings = "BookmarkPRoduct",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "page", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "size", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "nickname", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "category", type = String.class)
        }
)
@NamedNativeQuery(
        name = "getbookmarkCategoryCount",
        query = "call bookmarkCategoryCount(:nickname,:category)",
        resultClass = Integer.class
)
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,referencedColumnName="nickname",name="nickname")
    private User nickName;

}
