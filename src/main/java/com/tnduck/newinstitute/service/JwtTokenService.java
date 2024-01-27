package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.entity.JwtToken;
import com.tnduck.newinstitute.exception.NotFoundException;
import com.tnduck.newinstitute.repository.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:33 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {
    private final JwtTokenRepository jwtTokenRepository;

    private final MessageSourceService messageSourceService;

    /**
     * Find a JWT token by user id and refresh token.
     *
     * @param id           UUID
     * @param refreshToken String
     * @return JwtToken
     */
    public JwtToken findByUserIdAndRefreshToken(UUID id, String refreshToken) {
        return jwtTokenRepository.findByUserIdAndRefreshToken(id, refreshToken)
            .orElseThrow(() -> new NotFoundException(messageSourceService.get("not_found_with_param",
                new String[]{messageSourceService.get("token")})));
    }

    /**
     * Find a JWT token by token or refresh token.
     *
     * @param token String
     * @return JwtToken
     */
    public JwtToken findByTokenOrRefreshToken(String token) {
        return jwtTokenRepository.findByTokenOrRefreshToken(token, token)
            .orElseThrow(() -> new NotFoundException(messageSourceService.get("not_found_with_param",
                new String[]{messageSourceService.get("token")})));
    }

    /**
     * Save a JWT token.
     *
     * @param jwtToken JwtToken
     */
    public void save(JwtToken jwtToken) {
        jwtTokenRepository.save(jwtToken);
    }

    /**
     * Delete a JWT token.
     *
     * @param jwtToken JwtToken
     */
    public void delete(JwtToken jwtToken) {
        jwtTokenRepository.delete(jwtToken);
        log.info("Deleted token: {}", jwtToken);
    }
}
