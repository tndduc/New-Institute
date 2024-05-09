package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 9:40 PM
 */
@Entity
@Table(name = "lessons", indexes = {
        @Index(columnList = "title", name = "idx_lesson_title")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends AbstractBaseEntity{
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Column(name = "ordinal_number")
    private int ordinalNumber;
    @Column(name = "content")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
}
