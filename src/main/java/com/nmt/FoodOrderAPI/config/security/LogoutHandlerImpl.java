package com.nmt.FoodOrderAPI.config.security;

import com.nmt.FoodOrderAPI.config.utils.JwtUtils;
import com.nmt.FoodOrderAPI.entity.StaffTracking;
import com.nmt.FoodOrderAPI.repo.StaffTrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {
    private final JwtUtils jwtUtils;
    private final StaffTrackingRepository staffTrackingRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (request.getHeader("Authorization") != null && request.getHeader("Authorization").length() > 7) {
            String token = request.getHeader("Authorization").substring(7);
            Integer trackingId = jwtUtils.getTokenStaffTrackingId(token);
            if (trackingId != null) {
                StaffTracking staffTracking = staffTrackingRepository
                        .findById(trackingId)
                        .orElseThrow(() -> new NoSuchElementException("No such Staff Tracking found"));
                staffTracking.setLogoutTime(new Timestamp(System.currentTimeMillis()));

                Duration workingDuration = Duration.between(
                        staffTracking.getLoginTime().toInstant(),
                        staffTracking.getLogoutTime().toInstant()
                );
                int hoursPart = workingDuration.toHoursPart();
                int minutesPart = workingDuration.toMinutesPart();
                float durationHours = (float) (hoursPart + minutesPart / 60.0);
                staffTracking.setWorkingDuration(durationHours);

                staffTrackingRepository.save(staffTracking);
            }
        }
    }
}
