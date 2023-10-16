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
        String query = "SELECT st.StaffId AS Id, u.Fullname, SUM(st.Revenue) AS TotalRevenue, " +
                "CAST(SUM(st.WorkingDuration) AS decimal(10,2)) AS TotalDuration " +
                "FROM StaffTracking st INNER JOIN DBUser u ON st.StaffId = u.Id " +
                "WHERE FORMAT(st.LoginTime, 'MM-yyyy') = '" + monthOfYear +
                "' GROUP BY st.StaffId, u.Fullname";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(StaffTrackingStatisticResponse.class));
    }

    public List<String> getAllMonthStaffTrackingStatistic() {
        String query = "SELECT DISTINCT FORMAT(LoginTime, 'MM-yyyy') FROM StaffTracking " +
                "ORDER BY FORMAT(LoginTime, 'MM-yyyy') DESC";
        return jdbcTemplate.queryForList(query, String.class);
    }
}
