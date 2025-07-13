package com.singh.ecommerceapp.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    private String id;
    private UUID productId;  // Reference to Product ID
    private String comment;
    private Integer stars;



}
