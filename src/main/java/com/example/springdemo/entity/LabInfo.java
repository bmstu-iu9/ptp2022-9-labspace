package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
}
