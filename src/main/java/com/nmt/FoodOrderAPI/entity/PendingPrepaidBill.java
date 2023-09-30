package com.nmt.FoodOrderAPI.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingPrepaidBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Min(0)
    @Column(name = "TotalPrice", nullable = false)
    private Integer totalPrice;

    @Column(name = "Time", nullable = false)
    private Timestamp time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerId")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShipperId")
    private User shipper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PromotionId")
    private Promotion promotion;

    @OneToMany(
            mappedBy = "pendingPrepaidBill",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<PendingPrepaidBillItem> pendingPrepaidBillItemList;

}