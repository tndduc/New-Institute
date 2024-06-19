package com.tnduck.newinstitute.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tnduck.newinstitute.dto.validator.Level;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "description_short")
    private String descriptionShort;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private File file;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "level", nullable = false)
    private String level;
    @Column(name = "discount")
    private BigDecimal discount;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Column(name = "status_teacher", nullable = false, length = 50)
    private String statusTeacher;
    @Column(name = "status_admin", nullable = true, length = 50)
    private String statusAdmin;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "course_tags",
            joinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(
                            name = "fk_course_tags_course_id",
                            foreignKeyDefinition = "FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE"
                    ),
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id",
                    foreignKey = @ForeignKey(
                            name = "fk_course_tags_tag_id",
                            foreignKeyDefinition = "FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE"
                    ),
                    nullable = false
            ),
            uniqueConstraints = {
                    @UniqueConstraint(
                            columnNames = {"course_id", "tag_id"},
                            name = "uk_course_tags_course_id_tag_id"
                    )
            }
    )
    @Builder.Default
    private List<TagCourse> tags = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "course_categories",
            joinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(
                            name = "fk_course_categories_course_id",
                            foreignKeyDefinition = "FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE"
                    ),
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    foreignKey = @ForeignKey(
                            name = "fk_course_categories_tag_id",
                            foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE"
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
    private List<CategoryCourse> categories = new ArrayList<>();
}
