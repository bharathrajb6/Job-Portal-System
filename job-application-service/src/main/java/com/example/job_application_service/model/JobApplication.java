package com.example.job_application_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "job_application")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplication {

    @Id
    @Column(name = "application_id")
    private String applicationID;

    @Column(name = "job_id")
    private String jobID;

    @Column(name = "applicant_id")
    private String applicantID;

    @Column(name = "applied_at")
    @CurrentTimestamp
    private Timestamp appliedAt;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus status;
}
