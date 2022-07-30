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
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(
        name = "users"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User  {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(
                name = "user_id"
        )
        private Long id;

        @Column(
                name = "username",
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
        private boolean active;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "groupp_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Groupp groupp;

     @ElementCollection(targetClass =  Role.class,fetch = FetchType.EAGER)
     @CollectionTable(name = "role",joinColumns = @JoinColumn(name = "user_id"))
     @Enumerated(EnumType.STRING)
        private Set<Role> roles;



}
