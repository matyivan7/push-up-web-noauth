package com.project.push_up_web_noauth.repository;

import com.project.push_up_web_noauth.entity.PushUpTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushUpTrackerRepository extends JpaRepository<PushUpTracker, Long> {
    List<PushUpTracker> findByUsername(String username);
}
