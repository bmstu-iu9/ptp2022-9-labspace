package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Group group;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "role_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Role role;
}
