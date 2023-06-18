package com.nmt.FoodOrderAPI.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Percentage", nullable = false)
    private Integer percentage;

    @Column(name = "Active", nullable = false)
    private Boolean active;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ApplyingPrice", nullable = false)
    private Integer applyingPrice;

    @Column(name = "Image")
    private String image;

    @ManyToMany(mappedBy = "promotionList", fetch = FetchType.LAZY)
    private List<User> userList;

    @ManyToMany(mappedBy = "promotionList", fetch = FetchType.LAZY)
    private List<Bill> billList;
}
