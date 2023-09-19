package com.nmt.FoodOrderAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse implements Serializable {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private Integer quantity;
    private Integer unitPrice;
    private List<String> imageList;
}
