package com.moaguide.domain.CategoryContent;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryContentRepository extends JpaRepository<CategoryContent, Integer> {

	//카테고리 이름으로 조회
	Optional<CategoryContent> findByName(String name);
}
