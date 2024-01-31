package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:42 PM
 */
@Entity
@Table(name = "answers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends AbstractBaseEntity{
    @Column(name = "content")
    private String content;
    @Column(name = "description")
    private String description;
    @Column(name = "correct")
    private boolean correct;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;
}
