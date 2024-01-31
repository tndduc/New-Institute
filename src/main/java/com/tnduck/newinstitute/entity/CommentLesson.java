package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:29 PM
 */
@Entity
@Table(name = "comments_lessons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLesson extends AbstractBaseEntity{
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User student;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reply_to_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CommentLesson commentLesson;
    @Column(name = "comment")
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson _id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;
}
