package com.moaguide.controller;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.*;
import com.moaguide.service.view.NewsViewService;
import com.moaguide.service.view.ProductViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final NewsViewService newsViewService;
    private final AnnouncementService announcementService;


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
    public ResponseEntity<Boolean> notification(@RequestHeader(value = "Authorization",required = false) String jwt) {
        String Nickname;
        // 알림 상태 확인 true or false 반환
        if ( jwt!= null && jwt.startsWith("Bearer ")) {
            if(jwtUtil.isExpired(jwt.substring(7))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Nickname = jwtUtil.getNickname(jwt.substring(7));
        }else{
            Nickname = null;
        }
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

    // 홈페이지 상품 리스트 조회
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

    // 상품 조회수 증가
    @PostMapping("/product/view/{productId}")
    public ResponseEntity<String> InsertProductView(@PathVariable String productId, @RequestHeader(value = "Authorization", required = false) String jwt) {
        String nickname = "null";

        // JWT가 존재하는지, 그리고 유효한지 확인
        if (jwt != null && jwt.startsWith("Bearer ")) {
            String token = jwt.substring(7); // "Bearer " 제거한 토큰
            if (!jwtUtil.isExpired(token)) {
                // 토큰이 유효한 경우 닉네임 추출
                nickname = jwtUtil.getNickname(token);
            } else {
                // 토큰이 만료된 경우
                return ResponseEntity.status(401).body("토큰이 만료되었습니다. 다시 로그인 해주세요.");
            }
        }

        try {
            // 닉네임과 관계없이 서비스 호출
            productViewService.insert(productId, nickname);
            productService.viewupdate(productId);
            return ResponseEntity.ok("조회수 추가 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("조회수 추가 실패: " + e.getMessage());
        }
    }

    // 뉴스 조회수 추가
    @PostMapping("news/view/{NewsId}")
    public ResponseEntity<?> detail_check(@PathVariable Long NewsId, @RequestHeader(value = "Authorization", required = false) String jwt) {
        String nickname = "null";

        // JWT가 존재하는지, 그리고 유효한지 확인
        if (jwt != null && jwt.startsWith("Bearer ")) {
            String token = jwt.substring(7); // "Bearer " 제거한 토큰
            if (!jwtUtil.isExpired(token)) {
                // 토큰이 유효한 경우 닉네임 추출
                nickname = jwtUtil.getNickname(token);
            } else {
                // 토큰이 만료된 경우
                return ResponseEntity.status(401).body("토큰이 만료되었습니다. 다시 로그인 해주세요.");
            }
        }

        try {
            // 닉네임과 관계없이 서비스 호출
            newsViewService.insert(NewsId, nickname);
            newsService.viewupdate(NewsId);
            return ResponseEntity.ok("조회수 추가 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("조회수 추가 실패: " + e.getMessage());
        }
    }

    // 공지사항 조회
    @GetMapping("notice")
    public ResponseEntity<List<AnnouncementDto>> noticeList() {
        List<AnnouncementDto> noticeList = announcementService.getNotice();
        // 공지가 없으면 빈 리스트 반환
        if (noticeList == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(noticeList);
    }

    // 공지사항 상세 조회
    @GetMapping("notice/{id}")
    public ResponseEntity<?> noticeDetail(@PathVariable Long id) {
        AnnouncementDto notice = announcementService.getNoticeDetail(id);
        return ResponseEntity.ok(notice);
    }
}
