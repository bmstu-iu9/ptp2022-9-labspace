package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(
        name = "groups"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_sequence"
    )
    @Column(
            name = "group_id"
    )
    private Long groupId;

    @Column(
            name = "faculty",
            length = 30
    )
    private String faculty;

    @Column(
            name = "department",
            length = 30
    )
    private String department;

    @Column(
            name = "group_name",
            length = 30
    )
    private String groupName;
}
