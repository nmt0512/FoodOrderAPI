package com.nmt.FoodOrderAPI.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Nationalized
    @Column(name = "Name")
    private String name;

    @Column(name = "Code", nullable = false)
    private String code;

    @Nationalized
    @Column(name = "Description", length = 400)
    private String description;

    @CreatedDate
    @Column(name = "CreatedDate", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "UpdatedDate")
    private LocalDateTime updatedDate;

    @Nationalized
    @CreatedBy
    @Column(name = "CreatedBy", updatable = false, length = 100)
    private String createdBy;

    @Nationalized
    @LastModifiedBy
    @Column(name = "UpdatedBy", length = 100)
    private String updatedBy;

    @Column(name = "Quantity", nullable = false)
    @Min(0)
    private Integer quantity;

    @Column(name = "UnitPrice", nullable = false)
    @Min(0)
    private Integer unitPrice;

    @OneToMany(mappedBy = "product")
    private List<Image> imageList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<BillItem> billItemList;
}
