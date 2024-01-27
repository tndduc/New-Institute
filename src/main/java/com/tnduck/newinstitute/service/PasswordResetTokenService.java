package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.entity.PasswordResetToken;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.exception.BadRequestException;
import com.tnduck.newinstitute.exception.NotFoundException;
import com.tnduck.newinstitute.repository.PasswordResetTokenRepository;
import com.tnduck.newinstitute.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.tnduck.newinstitute.util.Constants.PASSWORD_RESET_TOKEN_LENGTH;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:31 PM
 */
@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final MessageSourceService messageSourceService;

    private final Long expiresIn;

    /**
     * Email verification token constructor.
     *
     * @param passwordResetTokenRepository PasswordResetTokenRepository
     * @param messageSourceService         MessageSourceService
     * @param expiresIn                    Long
     */
    public PasswordResetTokenService(
        PasswordResetTokenRepository passwordResetTokenRepository,
        MessageSourceService messageSourceService,
        @Value("${app.registration.password.token.expires-in}") Long expiresIn
    ) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.messageSourceService = messageSourceService;
        this.expiresIn = expiresIn;
    }

    /**
     * Is e-mail verification token expired?
     *
     * @param token PasswordResetToken
     * @return boolean
     */
    public boolean isPasswordResetTokenExpired(PasswordResetToken token) {
        return token.getExpirationDate().before(new Date());
    }

    /**
     * Create password reset token from user.
     *
     * @param user User
     * @return PasswordResetToken
     */
    public PasswordResetToken create(User user) {
        String newToken = new RandomStringGenerator(PASSWORD_RESET_TOKEN_LENGTH).next();
        Date expirationDate = Date.from(Instant.now().plusSeconds(expiresIn));
        Optional<PasswordResetToken> oldToken = passwordResetTokenRepository.findByUserId(user.getId());
        PasswordResetToken passwordResetToken;

        if (oldToken.isPresent()) {
            passwordResetToken = oldToken.get();
            passwordResetToken.setToken(newToken);
            passwordResetToken.setExpirationDate(expirationDate);
        } else {
            passwordResetToken = PasswordResetToken.builder()
                .user(user)
                .token(newToken)
                .expirationDate(Date.from(Instant.now().plusSeconds(expiresIn)))
                .build();
        }

        return passwordResetTokenRepository.save(passwordResetToken);
    }

    /**
     * Get password reset token by token.
     *
     * @param token String
     * @return PasswordResetToken
     */
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
            .orElseThrow(() -> new NotFoundException(messageSourceService.get("not_found_with_param",
                new String[]{messageSourceService.get("token")})));
    }

    /**
     * Get password reset token by token.
     *
     * @param token String
     * @return User
     */
    public User getUserByToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
            .orElseThrow(() -> new NotFoundException(messageSourceService.get("not_found_with_param",
                new String[]{messageSourceService.get("token")})));

        if (isPasswordResetTokenExpired(passwordResetToken)) {
            throw new BadRequestException(messageSourceService.get("expired_with_param",
                new String[]{messageSourceService.get("token")}));
        }

        return passwordResetToken.getUser();
    }

    /**
     * Delete password reset token by user ID.
     *
     * @param userId UUID
     */
    public void deleteByUserId(UUID userId) {
        passwordResetTokenRepository.deleteByUserId(userId);
    }
}
