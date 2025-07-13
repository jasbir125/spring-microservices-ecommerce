package com.singh.ecommerceapp.product.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document
public abstract class BaseEntity {

    @Indexed
    protected boolean active = true;
    @JsonIgnore
    protected String createdBy;
    @JsonIgnore
    protected String updatedBy;
    @CreatedDate
    @JsonIgnore
    protected LocalDateTime createdDate;
    @LastModifiedDate
    @JsonIgnore
    protected LocalDate updateDate;
    @Version
    @JsonIgnore
    private Long version;
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime updatedDate;
}