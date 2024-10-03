package com.moaguide.domain.news;

import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


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
                        @ColumnResult(name = "date", type = Date.class)
                }
        )
)
@NamedNativeQuery(
        name = "getBykeyword",
        query = "CALL getkeyword(:productId,:page,:size)",
        resultSetMapping = "NewsCustomDtoMapping"
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

    public News toDto(){
        return new News(id, keyword, title, link, date, description, views,category);
    }
}
