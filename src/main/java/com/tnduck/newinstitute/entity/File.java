package com.tnduck.newinstitute.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author ductn
 * @project The new institute
 * @created 22/02/2024 - 9:31 PM
 */
@Entity
@Table(name = "files")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File extends AbstractBaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "type")
    private String type;
    @Column(name = "status")
    private String status;
}
