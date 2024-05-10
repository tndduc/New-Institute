package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 8:55 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);
}
