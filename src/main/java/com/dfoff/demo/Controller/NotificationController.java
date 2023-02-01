package com.dfoff.demo.Controller;

import com.dfoff.demo.Annotation.Auth;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
