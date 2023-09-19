package com.nmt.FoodOrderAPI.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "StaffTracking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StaffId", nullable = false)
    private User staff;

    @CreatedDate
    @Column(name = "LoginTime", nullable = false, updatable = false)
    private Timestamp loginTime;

    @Column(name = "LogoutTime")
    private Timestamp logoutTime;

    @LastModifiedDate
    @Column(name = "LastUpdateTime", insertable = false)
    private Timestamp lastUpdateTime;

    @Column(name = "WorkingDuration", columnDefinition = "float(4)")
    private Float workingDuration;

    @Column(name = "Revenue", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer revenue;

    @PrePersist
    private void prePersist() {
        if (revenue == null)
            revenue = 0;
    }
}
