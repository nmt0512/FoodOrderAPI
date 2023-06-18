package com.nmt.FoodOrderAPI.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Code", nullable = false, length = 150)
    private String code;

    @Nationalized
    @Column(name = "Name", nullable = false, length = 150)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;
}
