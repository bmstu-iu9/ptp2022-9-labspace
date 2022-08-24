package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@JsonIgnoreProperties
@Entity
@Table(
        name = "groupp"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
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


    @ManyToMany
    private Set<LabInfo> labInfos;

}
