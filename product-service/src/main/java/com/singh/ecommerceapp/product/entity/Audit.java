package com.singh.ecommerceapp.product.entity;

import lombok.Data;

@Data
public class Audit {
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}