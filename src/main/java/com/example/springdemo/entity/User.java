package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Email(message = "Email is not correct!")
    private String email;

    @Column(
            name = "first_name"
    )
    @NotNull(message = "FirstName is compulsory!")
    private String firstName;

    @Column(
            name = "last_name"
    )
    @NotNull(message = "LastName is compulsory!")
    private String lastName;

    @Column(
            name = "patronymic"
    )
    private String patronymic;

    @Column(
            name = "tg_account"
    )
    @Pattern(regexp = "@[a-zA-Z]\\w{4,}", message = "Telegram username should be: @Username")
    private String tgAccount;

    @Column(
            name = "phone_number"
    )
    @Pattern(regexp = "\\+[0-9]{1,25}", message = "Phone number should start with + and contain only numbers")
    private String phoneNumber;

    @Column(
            name = "password"
    )
    @Length(min = 5, message = "Password should be at least 5 characters!")
    private String password;
    @Transient
    private String passwordConfirm;

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupp_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Groupp groupp;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String activationCode;

    @Column(
            name = "reset_password_token"
    )
    private String resetPasswordToken;



}
