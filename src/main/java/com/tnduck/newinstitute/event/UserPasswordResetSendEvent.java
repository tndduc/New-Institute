package com.tnduck.newinstitute.event;

import com.tnduck.newinstitute.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:42 PM
 */
@Getter
public class UserPasswordResetSendEvent extends ApplicationEvent {
    private final User user;

    public UserPasswordResetSendEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
