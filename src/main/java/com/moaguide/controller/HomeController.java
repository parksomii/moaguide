package com.moaguide.controller;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.*;
import com.moaguide.service.view.ProductViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/")
public class HomeController {
    private final NewsService newsService;
    private final StudyService articleService;
    private final NotificationService notificationService;
    private final JWTUtil jwtUtil;
    private final ProductService productService;
    private final ProductViewService productViewService;


    // 주요 리포트와 최신 이슈
    @GetMapping
    public ResponseEntity<Object> home(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ArticleSummaryDto> reportList = articleService.getSummary(pageable);
        List<NewsCustomDto> mainNews = newsService.getMainNews(pageable);
        ReportAndNewsDto reportAndNewsDto = new ReportAndNewsDto(reportList, mainNews);
        return ResponseEntity.ok(reportAndNewsDto);
    }

    // 홈페이지 알림상태 조회
    @GetMapping("notification")
    public ResponseEntity<Boolean> notification(@RequestHeader("Authorization") String token) {
        // 알림 상태 확인 true or false 반환
        String Nickname = jwtUtil.getNickname(token.substring(7));
        boolean status = notificationService.getNotification(Nickname);
        return ResponseEntity.ok(status);
    }

    // 알림창 리스트 조회
    @GetMapping("notificationList")
    public ResponseEntity<List<NotificationDto>> notificationList(@RequestHeader("Authorization") String token) {
        // 알림창 리스트 반환
        String Nickname = jwtUtil.getNickname(token.substring(7));
        List<NotificationDto> notificationList = notificationService.getNotificationList(Nickname);
        // 알림이 없으면 빈 리스트 반환
        if (notificationList == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(notificationList);
    }

    // 알림 읽으면 삭제
    @DeleteMapping("notification/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") Long id) {
        // 읽은 알림 삭제 시 성공 여부 반환
        boolean isDeleted = notificationService.deleteNotification(id);

        // 삭제 실패 시 에러 처리
        if (!isDeleted) {
            return ResponseEntity.badRequest().body("알림을 삭제하는데 실패했습니다.");
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("/home/list")
    public  ResponseEntity<?> homelist(@RequestParam String category){
        List<SummaryCustomDto> result = productService.home(category,"moaguide");
        if(result==null){
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }else {
            Map<String, Object> response = new HashMap<>();
            response.put("product", result);
            return ResponseEntity.ok(response);
        }
    }
    @GetMapping("product/{product_Id}")
    public ResponseEntity<String> InsertProductView(@PathVariable String productId, @RequestHeader("Authorization") String jwt) {
        if (jwt != null && jwt.startsWith("Bearer ") && !jwtUtil.isExpired(jwt.substring(7))) {
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            try {
                productViewService.insert(productId,nickname);
                productService.viewupdate(productId);
                return ResponseEntity.ok("조회수 추가 성공");
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body("조회수 추가 실패: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰");
        }
    }
}
