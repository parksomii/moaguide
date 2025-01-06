package com.moaguide.service;

import com.moaguide.domain.notification.Notification;
import com.moaguide.domain.notification.NotificationRepository;
import com.moaguide.domain.user.UserRepository;
import com.moaguide.dto.NewDto.customDto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // 알림 상태 확인
    public boolean getNotification(String nickname) {
        return notificationRepository.getNotification(nickname);
    }

    // 알림창 리스트 조회
    public List<NotificationDto> getNotificationList(String nickname,int nextCursor) {
        Pageable pageable = PageRequest.of(0,10);
        return notificationRepository.getNotificationList(nickname,pageable,nextCursor);
    }

    // 알림 삭제
    public boolean deleteNotification(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            notificationRepository.delete(notification.get());
            return true;  // 삭제 성공
        } else {
            return false;  // 해당 ID의 알림이 없어서 삭제 실패
        }
    }

    public void save(Notification notification) {
        int result =userRepository.findByNickname(notification.getNickName()).orElse(null).getMarketingConsent();
        if (result == 2 || result == 3) {
            notificationRepository.save(notification);
        }else{}
    }
}
