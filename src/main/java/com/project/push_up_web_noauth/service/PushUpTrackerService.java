package com.project.push_up_web_noauth.service;

import com.project.push_up_web_noauth.entity.PushUpTracker;
import com.project.push_up_web_noauth.repository.PushUpTrackerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PushUpTrackerService {

    private final PushUpTrackerRepository pushUpTrackerRepository;

    @Autowired
    public PushUpTrackerService(PushUpTrackerRepository pushUpTrackerRepository) {
        this.pushUpTrackerRepository = pushUpTrackerRepository;
    }

    public PushUpTracker createPushUpTracker(PushUpTracker pushUpTracker) {
        log.info("Create push up tracker method is called");

        PushUpTracker pushUpTrackerToSave = PushUpTracker.builder()
                .username(pushUpTracker.getUsername())
                .pushUpCount(pushUpTracker.getPushUpCount())
                .comment(pushUpTracker.getComment())
                .timeStamp(LocalDateTime.now())
                .build();

        return pushUpTrackerRepository.save(pushUpTrackerToSave);
    }
}

