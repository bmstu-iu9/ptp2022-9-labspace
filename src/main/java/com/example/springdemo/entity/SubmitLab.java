package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Please choose the file.")
    private String source;

    @Column(
            name = "src_code"
    )
    @NotNull(message = "Please choose the file.")
    private String src_code;

    @Column(
            name = "image"
    )
    @NotNull(message = "Please choose the file.")
    private String image;


    @Column(
            name = "send_date"
    )
    private Date sendDate;

    @Column(
            name = "mark"
    )
    private int mark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "lab_info_id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LabInfo labInfo;

    private String revisionComment;
    private Boolean onRevision = false;

    public boolean isOnRevision() {
        return onRevision;
    }
}
