package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:33 PM
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCourse extends AbstractBaseEntity{
    @Column(name = "name", nullable = false, length = 500)
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "course_categories",
            joinColumns = @JoinColumn(
                    name = "category_id",
                    foreignKey = @ForeignKey(
                            name = "fk_course_categories_category_id",
                            foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE"
                    ),
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(
                            name = "fk_course_categories_course_id",
                            foreignKeyDefinition = "FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE"
                    ),
                    nullable = false
            ),
            uniqueConstraints = {
                    @UniqueConstraint(
                            columnNames = {"course_id", "category_id"},
                            name = "uk_course_categories_course_id_category_id"
                    )
            }
    )
    @Builder.Default
    private Set<Course> courses = new HashSet<>();
}
