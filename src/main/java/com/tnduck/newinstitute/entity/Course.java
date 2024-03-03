package com.tnduck.newinstitute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:55 PM
 */
@Entity
@Table(name = "courses", indexes = {
        @Index(columnList = "name", name = "idx_courses_name")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends AbstractBaseEntity{
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private File file;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
}
