package com.tnduck.newinstitute.entity;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 8:30 PM
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}, name = "uk_users_email")
}, indexes = {
    @Index(columnList = "first_name", name = "idx_users_first_name"),
    @Index(columnList = "last_name", name = "idx_users_last_name")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractBaseEntity {
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "avatar", columnDefinition = "text")
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                name = "fk_user_roles_user_id",
                foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE"
            ),
            nullable = false
        ),
        inverseJoinColumns = @JoinColumn(
            name = "role_id",
            foreignKey = @ForeignKey(
                name = "fk_user_roles_role_id",
                foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE"
            ),
            nullable = false
        ),
        uniqueConstraints = {
            @UniqueConstraint(
                columnNames = {"user_id", "role_id"},
                name = "uk_user_roles_user_id_role_id"
            )
        }
    )
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmailVerificationToken emailVerificationToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PasswordResetToken passwordResetToken;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;


    /**
     * Get full name of user.
     *
     * @return String
     */
    public String getFullName() {
        return this.lastName + " " + this.firstName;
    }
}
