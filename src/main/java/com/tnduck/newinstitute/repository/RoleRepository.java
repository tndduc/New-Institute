package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Role;
import com.tnduck.newinstitute.util.Constants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(Constants.RoleEnum name);
}
