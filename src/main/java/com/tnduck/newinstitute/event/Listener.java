package com.tnduck.newinstitute.event;

import com.tnduck.newinstitute.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:42 PM
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class Listener {
    private final MailSenderService mailSenderService;

    @EventListener(UserEmailVerificationSendEvent.class)
    public void onUserEmailVerificationSendEvent(UserEmailVerificationSendEvent event) {
        log.info("[User e-mail verification mail send event listener] {} - {}",
            event.getUser().getEmail(), event.getUser().getId());
        mailSenderService.sendUserEmailVerification(event.getUser());
    }

    @EventListener(UserPasswordResetSendEvent.class)
    public void onUserPasswordResetSendEvent(UserPasswordResetSendEvent event) {
        log.info("[User password reset mail send event listener] {} - {}",
            event.getUser().getEmail(), event.getUser().getId());
        mailSenderService.sendUserPasswordReset(event.getUser());
    }
}
