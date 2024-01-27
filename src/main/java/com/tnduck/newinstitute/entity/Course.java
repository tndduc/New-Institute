package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "description", nullable = false, length = 50)
    private String description;
    @Column(name ="image")
    private String image;
    @Column(name = "price")
    private BigDecimal price;
    @JoinColumn(name="id_teacher", nullable=false,referencedColumnName="id")
    private User courseTeacher;
}
