package com.example.job_listing_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
