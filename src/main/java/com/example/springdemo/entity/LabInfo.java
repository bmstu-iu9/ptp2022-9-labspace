package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(
        name = "lab_info"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "lab_info_id"
    )
    private Long id;


    @Column(
            name = "upload_date"
    )
    private Date uploadDate = new Date(System.currentTimeMillis());
    private String description;
    @Column(
            name = "source"
    )
    private String source;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

    @ManyToMany(mappedBy = "labInfoSet",fetch = FetchType.LAZY)
    private Set<Groupp> groupps;
    @OneToMany(mappedBy = "labInfo",fetch = FetchType.LAZY )
    private Set<Deadline> deadlines;
    @Column(
            name = "is_visible"
    )
    private Boolean isVisible;

    @Override
    public String toString() {
        return "LabInfo{" +
                "id=" + id +
                ", uploadDate=" + uploadDate +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", name='" + name + '\'' +
                ", course=" + course +
                ", groupps=" + groupps +
                ", deadlines=" + deadlines +
                ", isVisible=" + isVisible +
                '}';
    }
}
