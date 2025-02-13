package com.example.job_listing_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "job_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCategory {

    @Id
    @Column(name = "category_id")
    private String categoryID;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Job> jobs;
}
