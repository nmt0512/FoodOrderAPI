package com.nmt.FoodOrderAPI.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "DBUser")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "Password", nullable = false, length = 100)
    private String password;

    @Column(name = "Gender")
    private Boolean gender;

    @Column(name = "Birthday")
    private LocalDate birthday;

    @Nationalized
    @Column(name = "Fullname", nullable = false, length = 150)
    private String fullname;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Role", nullable = false)
    private Integer role;

    @OneToMany(mappedBy = "customer")
    private List<Bill> billList;

    @OneToMany(mappedBy = "staff")
    private List<Bill> staffBillList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "DBUserPromotion",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "PromotionId")
    )
    private List<Promotion> promotionList;
}
