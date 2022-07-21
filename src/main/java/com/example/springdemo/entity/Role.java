package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        name = "role"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    private Long id;
    private String name;
    @Transient
    //@ManyToMany(mappedBy = "roles")
    private Set<Student> users;
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
