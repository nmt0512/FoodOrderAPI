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
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Min(0)
    @Column(name = "TotalPrice", nullable = false)
    private Integer totalPrice;

    @Column(name = "Time", nullable = false)
    private Timestamp time;

    @Column(name = "Status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "bill")
    private List<BillItem> billItemList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BillPromotion",
            joinColumns = @JoinColumn(name = "BillId"),
            inverseJoinColumns = @JoinColumn(name = "PromotionId")
    )
    private List<Promotion> promotionList;
}
