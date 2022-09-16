package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@JsonIgnoreProperties
@Entity
@Table(
        name = "lab_info"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "lab_info_id"
    )
    private Long id;


    @Column(
            name = "upload_date"
    )
    private Date uploadDate = new Date(System.currentTimeMillis());

    private String description;

    @Column(
            name = "source"
    )
    //@NotNull(message = "Please choose the file.")
    private String source;

    @Column(
            name = "name"
    )
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

    @ManyToMany
    private Set<Groupp> groupps;

    @Column(
            name = "is_visible"
    )
    private Boolean isVisible;
}
