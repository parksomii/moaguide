package com.moaguide.refactor.product.entity;

import com.moaguide.dto.NewDto.customDto.BookmarkProductDto;
import com.moaguide.refactor.user.entity.User;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.StoredProcedureParameter;
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
			@ColumnResult(name = "platform", type = String.class),
			@ColumnResult(name = "sale", type = Boolean.class)
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
	@JoinColumn(referencedColumnName = "product_Id", name = "product_Id")
	private Product productId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, referencedColumnName = "nickname", name = "nickname")
	private User nickName;

}
