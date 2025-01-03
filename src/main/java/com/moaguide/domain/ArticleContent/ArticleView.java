package com.moaguide.domain.ArticleContent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Article_View")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// nickname을 외래 키로 설정
	@Column(name = "nickname", nullable = false)
	private String nickname;

	// article_Id를 외래 키로 설정
	@Column(name = "article_Id", nullable = false)
	private Long articleId;

	@Column(name = "access_time")
	private Timestamp accessTime;

	public ArticleView(String nickname, Long articleId, Timestamp accessTime) {
		this.nickname = nickname;
		this.articleId = articleId;
		this.accessTime = accessTime;
	}
}
