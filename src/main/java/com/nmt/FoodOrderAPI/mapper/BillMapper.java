package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mapping(target = "time", dateFormat = "dd/MM/yyyy HH:mm")
    BillResponse toBillResponse(Bill bill);
}
