package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "submit_lab"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitLab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "submit_lab_id"
    )
    private Long id;

    @Column(
            name = "source"
    )
    private String source;

    @Column(
            name = "send_date"
    )
    private Date sendDate = new Date(System.currentTimeMillis());

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "student_id"
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "lab_info_id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LabInfo labInfo;
}
