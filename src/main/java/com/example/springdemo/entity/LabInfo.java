package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

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
    @SequenceGenerator(
            name = "lab_info_sequence",
            sequenceName = "lab_info_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lab_info_sequence"
    )
    @Column(
            name = "lab_info_id"
    )
    private Long labInfoId;

    @Column(
            name = "department",
            length = 30
    )
    private String department;

    @Column(
            name = "upload_date"
    )
    private Date uploadDate = new Date(System.currentTimeMillis());

    @Column(
            name = "source"
    )
    private String source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;
}
