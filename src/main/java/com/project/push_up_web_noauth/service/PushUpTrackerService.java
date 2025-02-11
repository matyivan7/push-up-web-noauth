package com.project.push_up_web_noauth.service;

import com.project.push_up_web_noauth.entity.PushUpTracker;
import com.project.push_up_web_noauth.repository.PushUpTrackerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        if (pushUpTracker.getUsername() == null) {
            throw new NullPointerException("Username is null");
        } else {
            PushUpTracker pushUpTrackerToSave = buildPushUpTracker(pushUpTracker);
            return pushUpTrackerRepository.save(pushUpTrackerToSave);
        }
    }

    public List<PushUpTracker> getAllPushUps() {
        log.info("Get all push ups method is called");

        return pushUpTrackerRepository.findAll();
    }

    public List<PushUpTracker> getAllPushUpsForUser(String username) {
        log.info("Get all push ups for User method is called");

        return pushUpTrackerRepository.findByUsername(username);
    }

    public void deletePushUpTracker(Long id) {
        log.info("Delete push up tracker method is called");

        pushUpTrackerRepository.deleteById(id);
    }

    private static PushUpTracker buildPushUpTracker(PushUpTracker pushUpTracker) {
        PushUpTracker pushUpTrackerToSave = PushUpTracker.builder()
                .username(pushUpTracker.getUsername())
                .pushUpCount(pushUpTracker.getPushUpCount())
                .comment(pushUpTracker.getComment())
                .timeStamp(LocalDateTime.now())
                .build();
        return pushUpTrackerToSave;
    }
}

