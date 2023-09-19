package com.nmt.FoodOrderAPI.config.auditing;

import com.nmt.FoodOrderAPI.entity.User;
import com.nmt.FoodOrderAPI.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<User> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return Optional.ofNullable(userRepository.findByUsername(username));
        }
        return Optional.empty();
    }
}

