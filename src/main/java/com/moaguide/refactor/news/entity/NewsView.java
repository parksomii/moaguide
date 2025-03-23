package com.moaguide.refactor.news.entity;

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
@Table(name = "News_View")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// nickname을 외래 키로 설정
	@Column(name = "nickname", nullable = false)
	private String nickname;

	// Product_Id를 외래 키로 설정
	@Column(name = "News_Id", nullable = false)
	private Long newsId;

	@Column(name = "access_time")
	private Timestamp accessTime;

	public NewsView(String nickname, Long newsId, Timestamp accessTime) {
		this.nickname = nickname;
		this.newsId = newsId;
		this.accessTime = accessTime;
	}
}
