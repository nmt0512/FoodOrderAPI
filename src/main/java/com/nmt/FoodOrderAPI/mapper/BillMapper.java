package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.entity.Bill;
import com.nmt.FoodOrderAPI.entity.Image;
import com.nmt.FoodOrderAPI.entity.PendingPrepaidBill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BillMapper {
    @Mapping(target = "time", dateFormat = "dd/MM/yyyy HH:mm")
    @Mapping(target = "staffName", source = "staff.fullname")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "usedPromotionResponse", source = "promotion")
    @Mapping(target = "billItemResponseList", source = "billItemList")
    BillResponse mapBillToBillResponse(Bill bill);

    @Mapping(target = "time", dateFormat = "dd/MM/yyyy HH:mm")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "usedPromotionResponse", source = "promotion")
    @Mapping(target = "billItemResponseList", source = "pendingPrepaidBillItemList")
    BillResponse mapPendingPrepaidBillToBillResponse(PendingPrepaidBill pendingPrepaidBill);

    default List<String> toImageLinks(List<Image> imageList) {
        return imageList.stream()
                .map(Image::getLink)
                .collect(Collectors.toList());
    }

}
