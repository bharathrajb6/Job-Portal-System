package com.example.job_listing_service.model;

import com.example.job_listing_service.model.constants.ExperienceLevel;
import com.example.job_listing_service.model.constants.JobState;
import com.example.job_listing_service.model.constants.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "job")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {

    @Id
    @Column(name = "job_id")
    private String jobID;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private double salary;

    @Column(name = "location")
    private String location;

    @Column(name = "job_type")
    @Enumerated(value = EnumType.STRING)
    private JobType jobType;

    @Column(name = "experience_level")
    @Enumerated(value = EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private Recruiters recruiters;

    @Column(name = "category_id")
    private String categoryID;

    @CurrentTimestamp
    @Column(name = "posted_at")
    private Timestamp postedAt;

    @Column(name = "job_state")
    @Enumerated(value = EnumType.STRING)
    private JobState jobState;
}
