package com.scriza.CaseManagement.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskId;

    private String taskDetails;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date lastModification;

    private String caseNumber;
    @Transient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cases_profile")
    private Cases cases ;

}
