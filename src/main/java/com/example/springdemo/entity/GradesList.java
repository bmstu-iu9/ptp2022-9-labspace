package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(
        name = "grades_list"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradesList {
    @Id
    @SequenceGenerator(
            name = "grades_list_sequence",
            sequenceName = "grades_list_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "grades_list_sequence"
    )
    @Column(
            name = "grades_list_id"
    )
    private Long gradesListId;

    @Column(
            name = "mark"
    )
    private int mark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "student_id"
    )
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "lab_info_id"
    )
    private LabInfo labInfo;
}
