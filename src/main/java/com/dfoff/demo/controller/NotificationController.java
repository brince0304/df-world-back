package com.dfoff.demo.controller;

import com.dfoff.demo.annotation.Auth;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    @Auth
    @GetMapping("/notifications/{id}")
    public String getNotification(@AuthenticationPrincipal UserAccount.PrincipalDto principal,@PathVariable Long id) {
        return notificationService.checkNotification(id);
    }

}
