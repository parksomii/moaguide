package com.moaguide.refactor.contents.controller;


import com.moaguide.refactor.contents.dto.BroadcastInfoDto;
import com.moaguide.refactor.contents.dto.ContentBaseDto;
import com.moaguide.refactor.contents.dto.ContentDetailDto;
import com.moaguide.refactor.contents.dto.ContentResponseDto;
import com.moaguide.refactor.contents.dto.ContentTopResponseDto;
import com.moaguide.refactor.contents.dto.ContentsSubResponseDto;
import com.moaguide.refactor.contents.dto.ExhibitInfoDto;
import com.moaguide.refactor.contents.dto.MovieInfoDto;
import com.moaguide.refactor.contents.dto.MovieStatsDto;
import com.moaguide.refactor.contents.dto.MovieSubDto;
import com.moaguide.refactor.contents.dto.PerformanceInfoDto;
import com.moaguide.refactor.contents.entity.movie.MoviePeople;
import com.moaguide.refactor.contents.entity.movie.MovieSchedule;
import com.moaguide.refactor.contents.service.ContentService;
import com.moaguide.refactor.contents.service.ContentSubService;
import com.moaguide.refactor.contents.service.MovieService;
import com.moaguide.refactor.jwt.util.JWTUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/content")
public class ContentRestController {

	private final ContentService contentService;
	private final MovieService movieService;
	private final ContentSubService contentSubService;
	private final JWTUtil jwtUtil;


	@GetMapping("/{product_Id}")
	public ResponseEntity<?> getContent(@PathVariable String product_Id,
		@RequestHeader(value = "Authorization", required = false) String jwt) {
		String Nickname;
		if (jwt != null && jwt.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(jwt.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			Nickname = jwtUtil.getNickname(jwt.substring(7));
		} else {
			Nickname = "null";
		}
		ContentDetailDto contentDetailDto = contentService.findDetail(product_Id, Nickname);
		return ResponseEntity.ok(new ContentTopResponseDto(contentDetailDto));
	}

	@GetMapping("/base/{product_Id}")
	public ResponseEntity<?> base(@PathVariable String product_Id, @RequestParam String genre) {
		ContentBaseDto base = contentService.findBase(product_Id, genre);
		if (genre.equals("MOVIE")) {
			MovieInfoDto movie = movieService.findmovie(product_Id);
			return ResponseEntity.ok(new ContentResponseDto(base, movie));
		} else if (genre.equals("EXHIBITION")) {
			ExhibitInfoDto exhibitInfoDto = contentSubService.findexhibit(product_Id);
			return ResponseEntity.ok(new ContentResponseDto(base, exhibitInfoDto));
		} else if (genre.equals("DRAMA") || genre.equals("ANIMATION")) {
			BroadcastInfoDto broadcast = contentSubService.findbroadcast(product_Id);
			return ResponseEntity.ok(new ContentResponseDto(base, broadcast));
		} else if (genre.equals("CULTURE")) {
			PerformanceInfoDto performance = contentSubService.findperformance(product_Id);
			return ResponseEntity.ok(new ContentResponseDto(base, performance));
		} else {
			if (base == null) {
				return ResponseEntity.ok(new HashMap<>());
			}
			return ResponseEntity.ok(new ContentResponseDto(base));
		}
	}

	@GetMapping("/sub/{product_Id}")
	public ResponseEntity<?> schedule(@PathVariable String product_Id) {
		List<MovieSchedule> movieScheduleDtos = movieService.findSechedule(product_Id);
		List<MovieStatsDto> movieStats = movieService.findStats(product_Id);
		return ResponseEntity.ok(new ContentsSubResponseDto(movieScheduleDtos, movieStats));
	}

	@GetMapping("/screen/{product_Id}")
	public ResponseEntity<?> screen(@PathVariable String product_Id) {
		List<MovieSubDto> screen = contentSubService.findScreen(product_Id);
		if (screen.isEmpty() || screen == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(screen);
	}

	@GetMapping("/tenscreen/ten/{product_Id}")
	public ResponseEntity<?> screenten(@PathVariable String product_Id) {
		Pageable pageable = Pageable.ofSize(10);
		List<MovieSubDto> screen = contentSubService.findScreenten(product_Id, pageable);
		if (screen.isEmpty() || screen == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(screen);
	}

	@GetMapping("/showtime/{product_Id}")
	public ResponseEntity<?> showtime(@PathVariable String product_Id) {
		List<MovieSubDto> showtimes = contentSubService.findshowtime(product_Id);
		if (showtimes.isEmpty() || showtimes == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(showtimes);
	}

	@GetMapping("/tenshowtime/ten/{product_Id}")
	public ResponseEntity<?> showtimeten(@PathVariable String product_Id) {
		Pageable pageable = Pageable.ofSize(10);
		List<MovieSubDto> screen = contentSubService.findshowtimeten(product_Id, pageable);
		if (screen.isEmpty() || screen == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(screen);
	}

	@GetMapping("/audience/{product_Id}")
	public ResponseEntity<?> Audience(@PathVariable String product_Id) {
		List<MovieSubDto> showtimes = contentSubService.findaudience(product_Id);
		if (showtimes.isEmpty() || showtimes == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(showtimes);
	}

	@GetMapping("/tenaudience/ten/{product_Id}")
	public ResponseEntity<?> Audienceten(@PathVariable String product_Id) {
		Pageable pageable = Pageable.ofSize(10);
		List<MovieSubDto> screen = contentSubService.findaudienceten(product_Id, pageable);
		if (screen.isEmpty() || screen == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(screen);
	}

	@GetMapping("/revenue/{product_Id}")
	public ResponseEntity<?> revenue(@PathVariable String product_Id) {
		List<MovieSubDto> showtimes = contentSubService.findrevenue(product_Id);
		if (showtimes.isEmpty() || showtimes == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(showtimes);
	}

	@GetMapping("/tenrevenue/ten/{product_Id}")
	public ResponseEntity<?> revenueten(@PathVariable String product_Id) {
		Pageable pageable = Pageable.ofSize(10);
		List<MovieSubDto> screen = contentSubService.findrevenueten(product_Id, pageable);
		if (screen.isEmpty() || screen == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(screen);
	}

	@GetMapping("/rank/{product_Id}")
	public ResponseEntity<?> rank(@PathVariable String product_Id) {
		List<MovieSubDto> showtimes = contentSubService.findrank(product_Id);
		if (showtimes.isEmpty() || showtimes == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(showtimes);
	}

	@GetMapping("/tenrank/ten/{product_Id}")
	public ResponseEntity<?> rankten(@PathVariable String product_Id) {
		Pageable pageable = Pageable.ofSize(10);
		List<MovieSubDto> screen = contentSubService.findrankten(product_Id, pageable);
		if (screen.isEmpty() || screen == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(screen);
	}

	@GetMapping("/people/{keyword}")
	public ResponseEntity<?> people(@PathVariable String keyword, @RequestParam int page) {
		Pageable pageable = Pageable.ofSize(3).withPage(page - 1);
		List<MoviePeople> moviePeople = movieService.findPeople(keyword, pageable);
		// null이나 빈 리스트 체크
		if (moviePeople == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(moviePeople);
	}

}
