package com.moaguide.refactor.notice.service;

import com.moaguide.refactor.notice.dto.NotificationDto;
import com.moaguide.refactor.notice.entity.Notification;
import com.moaguide.refactor.notice.repository.NotificationRepository;
import com.moaguide.refactor.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public List<NotificationDto> getNotificationList(String nickname, int nextCursor) {
		Pageable pageable = PageRequest.of(0, 10);
		return notificationRepository.getNotificationList(nickname, pageable, nextCursor);
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

	@Transactional
	@Async
	public void save(Notification notification) {
		int result = userRepository.findByNickname(notification.getNickName()).orElse(null)
			.getMarketingConsent();
		if (result == 2 || result == 3) {
			notificationRepository.save(notification);
		} else {
		}
	}
}
