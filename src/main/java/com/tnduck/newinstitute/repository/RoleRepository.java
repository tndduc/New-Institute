package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Role;
import com.tnduck.newinstitute.util.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 8:55 PM
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(Constants.RoleEnum name);
}
