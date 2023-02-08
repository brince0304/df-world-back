package com.dfoff.demo.service;

import com.dfoff.demo.domain.Board;
import com.dfoff.demo.domain.BoardComment;
import com.dfoff.demo.domain.enums.useraccount.NotificationType;
import com.dfoff.demo.domain.Notification;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.repository.NotificationRepository;
import com.dfoff.demo.utils.NotificationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDate;

import static com.dfoff.demo.controller.SseController.sseEmitters;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void saveBoardCommentNotification(UserAccount.UserAccountDto dto, BoardComment.BoardCommentDto commentDto, String nickname,NotificationType type){
        Notification notification = Notification.of(dto.toEntity(),commentDto.toEntity(),type, NotificationUtil.getNotificationContentFrom(type.name(), nickname, commentDto.getCommentContent()));
        notificationRepository.save(notification);
        if (sseEmitters.containsKey(dto.userId()) && type!=NotificationType.DELETE_COMMENT  && type!=NotificationType.DELETE_CHILDREN_COMMENT ) {
            SseEmitter sseEmitter = sseEmitters.get(dto.userId());
            try {
                sseEmitter.send(SseEmitter.event().name("event").data(notification.getNotificationContent()));
                sseEmitter.send(SseEmitter.event().name("notificationId").data(notification.getId()));
            } catch (Exception e) {
                sseEmitters.remove(dto.userId());
            }
        }
    }


    public void saveBoardNotification(UserAccount.UserAccountDto dto, Board.BoardDto boardDto, String nickname,NotificationType type){
        Notification notification = Notification.of(dto.toEntity(),boardDto.toEntity(),type, NotificationUtil.getNotificationContentFrom(type.name(), nickname,""));
        notificationRepository.save(notification);
        if (sseEmitters.containsKey(dto.userId())) {
            SseEmitter sseEmitter = sseEmitters.get(dto.userId());
            try {
                sseEmitter.send(SseEmitter.event().name("event").data(notification.getNotificationContent()));
                sseEmitter.send(SseEmitter.event().name("notificationId").data(notification.getId()));

            } catch (Exception e) {
                sseEmitters.remove(dto.userId());
            }
        }
    }

    public Page<Notification.UserLogResponse> getUserNotifications(String userId, Pageable pageable) {
        Page<Notification> userLog = notificationRepository.getNotificationsByUserAccount_UserId(userId, pageable);
        userLog.forEach(o-> o.setChecked(Boolean.TRUE));
        return userLog.map(Notification.UserLogResponse::from);
    }

    public Long getUncheckedNotificationCount(String userId) {
        return notificationRepository.getUnCheckedNotificationCountByUserId(userId);
    }

    public String checkNotification(Long id){
        Notification notification = notificationRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 알림이 없습니다."));
        notification.setChecked(Boolean.TRUE);
        return "/boards/"+notification.getBoardId();
    }
}
