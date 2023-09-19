package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.ProductResponse;
import com.nmt.FoodOrderAPI.entity.Image;
import com.nmt.FoodOrderAPI.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    List<ProductResponse> toProductResponseList(List<Product> productList);

    default List<String> toImageLinks(List<Image> imageList) {
        return imageList.stream()
                .map(Image::getLink)
                .collect(Collectors.toList());
    }
}
