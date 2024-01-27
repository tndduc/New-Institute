package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.JwtToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 8:55 PM
 */
public interface JwtTokenRepository extends CrudRepository<JwtToken, UUID> {
    Optional<JwtToken> findByTokenOrRefreshToken(String token, String refreshToken);

    Optional<JwtToken> findByUserIdAndRefreshToken(UUID id, String refreshToken);
}
