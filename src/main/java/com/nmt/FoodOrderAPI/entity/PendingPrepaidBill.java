package com.nmt.FoodOrderAPI.entity;

import com.nmt.FoodOrderAPI.exception.PendingPrepaidUpdateException;
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

    @Column(name = "Received", columnDefinition = "bit DEFAULT 0")
    private Boolean received;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PromotionId")
    private Promotion promotion;

    @OneToMany(mappedBy = "pendingPrepaidBill", cascade = CascadeType.ALL)
    private List<PendingPrepaidBillItem> pendingPrepaidBillItemList;

    @PrePersist
    public void prePersist() {
        if (received == null)
            received = false;
    }

    @PreUpdate
    public void preUpdate() {
        if (received)
            throw new PendingPrepaidUpdateException("Bill was received by a shipper and update failed");
    }
}
