package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(
        name = "student"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(
                name = "user_id"
        )
        private Long id;

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
        @JoinColumn(name = "groupp_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Groupp groupp;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "role_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Role role;
}
