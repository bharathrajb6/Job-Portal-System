package com.example.job_listing_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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

    @Column(name = "company_id")
    private String companyID;

    @Column(name = "position")
    private String position;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

}
