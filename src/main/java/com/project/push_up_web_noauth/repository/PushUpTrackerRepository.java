package com.project.push_up_web_noauth.repository;

import com.project.push_up_web_noauth.entity.PushUpTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushUpTrackerRepository extends JpaRepository<PushUpTracker, Long> {
}
