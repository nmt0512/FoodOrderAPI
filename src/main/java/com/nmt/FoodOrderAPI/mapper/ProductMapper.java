package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.ProductRequest;
import com.nmt.FoodOrderAPI.dto.ProductResponse;
import com.nmt.FoodOrderAPI.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product entity);

    Product toProduct(ProductRequest productRequest);
}
