package com.nmt.FoodOrderAPI.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Min(0)
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Min(0)
    @Column(name = "Price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BillId")
    private Bill bill;
}
