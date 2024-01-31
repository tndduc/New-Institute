package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:34 PM
 */
@Entity
@Table(name = "scores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score extends AbstractBaseEntity{
    @Column(name = "score")
    private BigDecimal score;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
