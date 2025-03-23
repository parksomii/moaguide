package com.moaguide.refactor.news.entity;

import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "News")
@SqlResultSetMapping(
	name = "NewsCustomDtoMapping",
	classes = @ConstructorResult(
		targetClass = NewsCustomDto.class,
		columns = {
			@ColumnResult(name = "id", type = Long.class),
			@ColumnResult(name = "title", type = String.class),
			@ColumnResult(name = "description", type = String.class),
			@ColumnResult(name = "category", type = String.class),
			@ColumnResult(name = "link", type = String.class),
			@ColumnResult(name = "date", type = Date.class),
			@ColumnResult(name = "imgUrl", type = String.class)
		}
	)
)
@NamedNativeQuery(
	name = "getBykeyword",
	query = "CALL getkeyword(:productId,:page,:size)",
	resultSetMapping = "NewsCustomDtoMapping"
)
@NamedNativeQuery(
	name = "getNewsCount",
	query = "call getNewsCount(:productId)",
	resultClass = Integer.class
)
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String keyword;

	private String title;

	private String link;

	private Date date;

	private String description;

	private int views;

	private String category;

	private String img_url;

	public News toDto() {
		return new News(id, keyword, title, link, date, description, views, category, img_url);
	}
}
