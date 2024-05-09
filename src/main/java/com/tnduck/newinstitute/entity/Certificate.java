package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "certificates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate extends AbstractBaseEntity{
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quizResult_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuizResult quizResult;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

}
