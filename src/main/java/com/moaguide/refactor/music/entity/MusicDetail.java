package com.moaguide.refactor.music.entity;

import com.moaguide.refactor.music.dto.MusicReponseDto;
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
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@SqlResultSetMapping(
	name = "musicDetailDtoMapping",
	classes = @ConstructorResult(
		targetClass = MusicReponseDto.class,
		columns = {
			@ColumnResult(name = "product_Id", type = String.class),
			@ColumnResult(name = "category", type = String.class),
			@ColumnResult(name = "platform", type = String.class),
			@ColumnResult(name = "name", type = String.class),
			@ColumnResult(name = "singer", type = String.class),
			@ColumnResult(name = "price", type = Integer.class),
			@ColumnResult(name = "priceRate", type = Double.class),
			@ColumnResult(name = "totalPrice", type = Long.class),
			@ColumnResult(name = "lastDivide", type = Double.class),
			@ColumnResult(name = "lastDivide_rate", type = Double.class),
			@ColumnResult(name = "divideCycle", type = Integer.class),
			@ColumnResult(name = "link", type = String.class),
			@ColumnResult(name = "bookmark", type = Boolean.class),
			@ColumnResult(name = "yearDvide", type = Integer.class),
			@ColumnResult(name = "yearDvideRate", type = Double.class)
		}
	)
)
@NamedStoredProcedureQuery(
	name = "music_detail",
	procedureName = "music_detail",
	resultSetMappings = "musicDetailDtoMapping",
	parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "in_Product_Id", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "nickname", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "year", type = int.class)
	}
)
@Table(name = "MusicDetail")
public class MusicDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
	private Product productId;

	@Column(name = "introduce_song")
	private String introduceSong;

	@Column(name = "genre", length = 30)
	private String genre;

	@Column(name = "singer", length = 30)
	private String singer;

	@Column(name = "writer", length = 30)
	private String writer;

	@Column(name = "composing", length = 30)
	private String composing;

	@Column(name = "arrangements", length = 30)
	private String arrangements;

	@Column(name = "announcement_Date")
	@Temporal(TemporalType.DATE)
	private Date announcementDate;

	@Column(name = "type", length = 10)
	private String type;

	@Column(name = "melon_Id")
	private String melonId;

	@Column(name = "youtube_url")
	private String youtubeUrl;

	private String keyword;

	@Column(name = "issue_day")
	@Temporal(TemporalType.DATE)
	private Date issuDay;
}
