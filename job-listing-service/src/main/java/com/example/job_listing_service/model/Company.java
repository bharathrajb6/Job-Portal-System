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
@Table(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    @Id
    @Column(name = "company_id")
    private String companyID;

    @Column(name = "company_name", unique = true, nullable = false)
    private String companyName;

    @Column(name = "website", nullable = false, unique = true)
    private String website;

    @Column(name = "industry")
    private String industry;

    @Column(name = "location")
    private String location;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Job> jobs;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Recruiters> recruiters;
}
