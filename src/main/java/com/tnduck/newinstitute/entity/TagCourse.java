package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:33 PM
 */
@Entity
@Table(name = "tag_courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagCourse extends AbstractBaseEntity{
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
}
