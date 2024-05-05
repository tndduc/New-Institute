package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:50 PM
 */
@Entity
@Table(name = "videos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video extends AbstractBaseEntity{
    @Column(name = "title", nullable = true)
    private String title;
    @Column(name = "duration", nullable = true)
    private String duration;
    @Column(name = "url", nullable = false)
    private String url;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;
}
