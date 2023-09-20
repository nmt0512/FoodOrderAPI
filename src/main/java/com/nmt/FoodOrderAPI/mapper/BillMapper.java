package com.nmt.FoodOrderAPI.mapper;

import com.nmt.FoodOrderAPI.dto.BillResponse;
import com.nmt.FoodOrderAPI.entity.*;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "billItemList", ignore = true)
    Bill mapPendingPrepaidBillToBill(PendingPrepaidBill pendingPrepaidBill);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bill", ignore = true)
    BillItem mapPendingPrepaidBillItemToBillItem(PendingPrepaidBillItem pendingPrepaidBillItem);

    default List<String> toImageLinks(List<Image> imageList) {
        return imageList.stream()
                .map(Image::getLink)
                .collect(Collectors.toList());
    }

}
