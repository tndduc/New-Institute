package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:13 PM
 */
@Entity
@Table(name = "reviews_lesson")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCourse extends AbstractBaseEntity{
    @Column(name = "review")
    private String review;
    @Column(name = "star")
    private int star;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User student;
}
