package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.dto.StaffTrackingStatisticResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StaffTrackingStatisticRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<StaffTrackingStatisticResponse> getStaffTrackingStatisticByMonthOfYear(String monthOfYear) {
        String query = "SELECT st.StaffId AS Id, u.Fullname, SUM(st.Revenue) AS TotalRevenue, SUM(st.WorkingDuration) AS TotalDuration " +
                "FROM DBUser u JOIN StaffTracking st ON u.Id = st.StaffId " +
                "WHERE FORMAT(st.LoginTime, 'MM/yyyy') = '" + monthOfYear +
                "' GROUP BY st.StaffId, u.Fullname";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(StaffTrackingStatisticResponse.class));
    }
}
