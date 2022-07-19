package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(
        name = "student"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
        @Id
        @SequenceGenerator(
                name = "student_sequence",
                sequenceName = "student_sequence"
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "student_sequence"
        )
        @Column(
                name = "student_id"
        )
        private Long studentId;

        @Column(
                name = "email",
                unique = true
        )
        @Email
        private String email;

        @Column(
                name = "first_name"
        )
        private String firstName;

        @Column(
                name = "last_name"
        )
        private String lastName;

        @Column(
                name = "password"
        )
        private String password;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "group_id")
        private Group group;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "role_id")
        private Role role;
}
