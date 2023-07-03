package com.nmt.FoodOrderAPI.repo;

import com.nmt.FoodOrderAPI.dto.StatisticResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatisticRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<StatisticResponse> getMonthlyStatisticByYear(int year) {
        StringBuilder query = new StringBuilder("SELECT FORMAT(Time, 'MM/yyyy') AS Time, SUM(TotalPrice) AS Revenue ")
                .append("FROM Bill WHERE Status = 2 AND YEAR(Time) = ")
                .append(year)
                .append(" GROUP BY FORMAT(Time, 'MM/yyyy')");
        return jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(StatisticResponse.class));
    }

    public List<Integer> getAllStatisticYear() {
        String query = "SELECT DISTINCT YEAR(Time) FROM Bill ORDER BY YEAR(Time) ASC";
        return jdbcTemplate.queryForList(query, Integer.TYPE);
    }

    public StatisticResponse getRevenueByDay(String day) {
        StringBuilder query = new StringBuilder("SELECT FORMAT(Time, 'dd/MM/yyyy') AS Time, SUM(TotalPrice) AS Revenue ")
                .append("FROM Bill WHERE Status = 2 AND FORMAT(Time, 'dd/MM/yyyy') = '")
                .append(day)
                .append("' GROUP BY FORMAT(Time, 'dd/MM/yyyy')");
        try {
            return jdbcTemplate.queryForObject(query.toString(), (rs, rowNum) -> StatisticResponse.builder()
                    .time(rs.getNString("Time"))
                    .revenue(rs.getInt("Revenue"))
                    .build());
        } catch (EmptyResultDataAccessException e) {
            return StatisticResponse.builder()
                    .time(day)
                    .revenue(0)
                    .build();
        }

    }
}
