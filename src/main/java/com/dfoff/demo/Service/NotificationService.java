package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.Board;
import com.dfoff.demo.Domain.BoardComment;
import com.dfoff.demo.Domain.EnumType.UserAccount.NotificationType;
import com.dfoff.demo.Domain.Notification;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.NotificationRepository;
import com.dfoff.demo.Util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDate;

import static com.dfoff.demo.Controller.SseController.sseEmitters;

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
    public void deleteNotificationsByDate(LocalDate date){
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
