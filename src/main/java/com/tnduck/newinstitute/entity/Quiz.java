package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
@Entity
@Table(name = "quizzes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz extends AbstractBaseEntity{
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private int time;
    @Column(nullable = false)
    private boolean isHaveTime;
    @Column(nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Unit unit;
    @Column(nullable = false)
    private boolean isFinalExam;
}
