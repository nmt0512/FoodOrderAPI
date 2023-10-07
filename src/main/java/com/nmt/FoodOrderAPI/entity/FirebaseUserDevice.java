package com.nmt.FoodOrderAPI.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"UserId", "FirebaseToken"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseUserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "DeviceKey")
    private String deviceKey;

    @Column(name = "FirebaseToken")
    private String firebaseToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private User user;

}
