package com.moaguide.domain.notification;

import com.moaguide.dto.NewDto.customDto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // 알림 상태 확인 - 데이터가 있으면 true, 없으면 false 로 반환
    @Query("SELECT COUNT(n) > 0 FROM Notification n WHERE n.nickName = :nickname")
    boolean getNotification(@Param("nickname") String nickname);

    // 알림창 리스트 조회
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.NotificationDto(n.id, n.link, n.message, n.Date) FROM Notification n WHERE n.nickName = :nickname and n.id>:Cursor order by n.id")
    List<NotificationDto> getNotificationList(@Param("nickname") String nickname, Pageable pageable, @Param("Cursor") int nextCursor);
}
