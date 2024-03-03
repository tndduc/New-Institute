package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.user.CreateUserRequest;
import com.tnduck.newinstitute.entity.Role;
import com.tnduck.newinstitute.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:21 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DummyDataService implements CommandLineRunner {
    private final RoleService roleService;

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (roleService.count() == 0) {
            log.info("Creating roles...");
            createRoles();
            log.info("Roles created.");
        }

        if (userService.count() == 0) {
            log.info("Creating users...");
            createUsers();
            log.info("Users created.");
        }
    }

    /**
     * Create roles.
     */
    private void createRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.builder().name(Constants.RoleEnum.ADMIN).build());
        roleList.add(Role.builder().name(Constants.RoleEnum.USER).build());
        roleList.add(Role.builder().name(Constants.RoleEnum.TEACHER).build());
        roleService.saveList(roleList);
    }

    /**
     * Create users.
     *
     * @throws BindException Bind exception
     */
    private void createUsers() throws BindException {
        List<String> roleList = new ArrayList<>();
        roleList.add(Constants.RoleEnum.ADMIN.getValue());
        roleList.add(Constants.RoleEnum.USER.getValue());
        roleList.add(Constants.RoleEnum.TEACHER.getValue());
        String defaultPassword = "P@sswd123.";

        userService.create(CreateUserRequest.builder()
            .email("admin@example.com")
            .password(defaultPassword)
            .firstName("John")
            .lastName("DOE")
            .roles(roleList)
            .isEmailVerified(true)
            .isBlocked(false)
            .build());
        userService.create(CreateUserRequest.builder()
                .email("teacher@example.com")
                .password(defaultPassword)
                .firstName("John")
                .lastName("DOE")
                .roles(roleList)
                .isEmailVerified(true)
                .isBlocked(false)
                .build());

        userService.create(CreateUserRequest.builder()
            .email("user@example.com")
            .password(defaultPassword)
            .firstName("Jane")
            .lastName("DOE")
            .roles(List.of(roleList.get(1)))
            .isEmailVerified(true)
            .isBlocked(false)
            .build());
    }
}
