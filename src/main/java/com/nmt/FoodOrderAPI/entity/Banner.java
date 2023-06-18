package com.nmt.FoodOrderAPI.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Banner")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Link")
    private String link;
}
