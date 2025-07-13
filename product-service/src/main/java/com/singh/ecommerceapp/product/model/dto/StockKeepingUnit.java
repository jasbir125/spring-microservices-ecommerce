package com.singh.ecommerceapp.product.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StockKeepingUnit {

    private String color;
    private String size;
    private String brand;
    private List<String> categories;
}
