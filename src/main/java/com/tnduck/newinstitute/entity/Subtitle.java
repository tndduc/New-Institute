package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:54 PM
 */
@Entity
@Table(name = "subtitles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subtitle extends AbstractBaseEntity{
    @CreationTimestamp
    @Column(name = "start_at")
    private LocalDateTime startAt;
    @CreationTimestamp
    @Column(name = "end_at")
    private LocalDateTime endAt;
    @Column(name = "text")
    private String text;
    @Column(name = "language")
    private String language;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Video video;
}
