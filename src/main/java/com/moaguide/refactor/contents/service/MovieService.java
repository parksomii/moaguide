package com.moaguide.refactor.contents.service;


import com.moaguide.refactor.contents.dto.MovieInfoDto;
import com.moaguide.refactor.contents.dto.MovieStatsDto;
import com.moaguide.refactor.contents.entity.movie.MoviePeople;
import com.moaguide.refactor.contents.entity.movie.MovieSchedule;
import com.moaguide.refactor.contents.repository.movie.MoviePeopleRepository;
import com.moaguide.refactor.contents.repository.movie.MovieRepository;
import com.moaguide.refactor.contents.repository.movie.MovieScheduleRepository;
import com.moaguide.refactor.contents.repository.movie.MovieStatsRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MovieService {

	private final MovieRepository movieRepository;
	private final MovieStatsRepository movieStatsRepository;
	private final MovieScheduleRepository movieScheduleRepository;
	private final MoviePeopleRepository moviePeopleRepository;

	public MovieInfoDto findmovie(String productId) {
		return movieRepository.findByProductId(productId);
	}

	@Transactional
	public List<MovieSchedule> findSechedule(String productId) {
		return movieScheduleRepository.findByschedule(productId);
	}

	public List<MovieStatsDto> findStats(String productId) {
		return movieStatsRepository.findByProductId(productId);
	}

	public List<MoviePeople> findPeople(String keyword, Pageable pageable) {
		return moviePeopleRepository.findByname(keyword, pageable);
	}
}
