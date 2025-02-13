package com.example.job_listing_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "recruiters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recruiters {

    @Id
    @Column(name = "recruiter_id")
    private String recruiterID;

    @Column(name = "username", unique = true)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "position")
    private String position;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "recruiters", fetch = FetchType.LAZY)
    private List<Job> jobs;
}
