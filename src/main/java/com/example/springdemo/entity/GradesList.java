package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "grades_list_id"
    )
    private Long id;

    @Column(
            name = "mark"
    )
    private int mark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "submit_lab_id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmitLab submitLab;
}
