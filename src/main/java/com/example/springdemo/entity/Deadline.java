package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(
        name = "deadline"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deadline implements Comparable<Deadline> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deadline_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "lab_info_id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LabInfo labInfo;

    @Column(
            name = "max_mark"
    )
    private Integer maxMark;

    @Column(
            name = "deadline_date"
    )
    private Date deadlineDate;

    @Override
    public int compareTo(Deadline o) {
        return this.getDeadlineDate().compareTo(o.getDeadlineDate());
    }
}
