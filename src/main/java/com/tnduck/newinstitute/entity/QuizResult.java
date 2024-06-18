package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_results")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResult extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Column(nullable = false)
    private int score;
    @ManyToMany
    @JoinTable(
            name = "quiz_result_choice",
            joinColumns = @JoinColumn(name = "quiz_result_id"),
            inverseJoinColumns = @JoinColumn(name = "choice_id")
    )
    private List<Choice> choices = new ArrayList<>();
}
