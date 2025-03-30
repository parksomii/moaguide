package com.moaguide.refactor.product.controller;


import com.moaguide.refactor.news.dto.NewsCustomDto;
import com.moaguide.refactor.news.service.NewsService;
import com.moaguide.refactor.product.dto.DetailNewsResponseDto;
import com.moaguide.refactor.product.service.ProductDetailService;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/detail")
@AllArgsConstructor
@RestController
public class ProductDetailController {

	private final NewsService newsService;
	private final ProductDetailService productDetailService;

	@GetMapping("/news/{product_Id}")
	public ResponseEntity<Object> news(@PathVariable String product_Id, @RequestParam int page,
		@RequestParam int size) {
		List<NewsCustomDto> newsDtos = newsService.findBydetail(product_Id, page - 1, size);
		int total = newsService.findByDetailCount(product_Id);
		return ResponseEntity.ok(new DetailNewsResponseDto(newsDtos, page, size, total));
	}

	public ResponseEntity<Object> Topdetail(@PathVariable String category,
		@PathVariable String product_Id,
		@RequestHeader(value = "Authorization", required = false) String jwt) {

		try{
		switch (category) {
			case "building":
				return productDetailService.buildingDetail(product_Id, nickname);
			case "music":
				return productDetailService.musicDetail(product_Id, nickname);
			case "contents":
				return productDetailService.contentsDetail(product_Id, nickname);
			case "art":
				return productDetailService.artDetail(product_Id, nickname);
			case "cow":
				return productDetailService.cowDetail(product_Id, nickname);
			default:
				return ResponseEntity.ok(new HashMap<>());
		}
		}catch (NullPointerException e){
			return ResponseEntity.ok(new HashMap<>());
		}
		catch (Exception e){
			return "내부 서버 에러 Exception 추가";
		}
	}
}
