package com.nmt.FoodOrderAPI.entity;

import lombok.*;
import org.hibernate.annotations.Nationalized;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StaffId")
    private User staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerId", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PromotionId")
    private Promotion promotion;

    @OneToMany(mappedBy = "bill")
    private List<BillItem> billItemList;
}
