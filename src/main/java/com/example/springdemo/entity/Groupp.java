package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@JsonIgnoreProperties
@Entity
@Table(
        name = "groupp"
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

@Builder
public class Groupp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "groupp_id"
    )
    private Long id;

    private String faculty;

    private String department;

    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User grouppLeader;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Groupp groupp = (Groupp) o;
        return id != null && Objects.equals(id, groupp.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
