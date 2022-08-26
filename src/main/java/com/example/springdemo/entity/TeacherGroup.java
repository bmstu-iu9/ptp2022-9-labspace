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
        name = "teacher_group"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_group_id")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "teacher_id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    public User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "group_id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Groupp groupp;
}
