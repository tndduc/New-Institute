package com.tnduck.newinstitute.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "units")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unit extends AbstractBaseEntity{
    @Column(name = "title", nullable = true)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "ordinal_number")
    private int ordinalNumber;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "isPreview")
    private Boolean isPreview;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;
}
