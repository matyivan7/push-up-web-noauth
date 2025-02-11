package com.project.push_up_web_noauth.service;

import com.project.push_up_web_noauth.entity.PushUpTracker;
import com.project.push_up_web_noauth.exception.UsernameNotFoundException;
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

    public PushUpTracker createPushUpTracker(String comment, Integer pushUpCount, String username) {
        log.info("Create push up tracker method is called");

        PushUpTracker pushUpTrackerToSave = buildPushUpTracker(comment, pushUpCount, username);
        return pushUpTrackerRepository.save(pushUpTrackerToSave);
    }

    public List<PushUpTracker> getAllPushUps() {
        log.info("Get all push ups method is called");

        return pushUpTrackerRepository.findAll();
    }

    public List<PushUpTracker> getAllPushUpsForUser(String username) {
        log.info("Get all push ups for User method is called");

        if (username.isEmpty() || pushUpTrackerRepository.findByUsername(username.toLowerCase()).isEmpty()) {
            throw new UsernameNotFoundException("Invalid username: " + username);
        } else {
            return pushUpTrackerRepository.findByUsername(username.toLowerCase());
        }
    }

    public void deletePushUpTracker(Long id) {
        log.info("Delete push up tracker method is called");

        pushUpTrackerRepository.deleteById(id);
    }

    private static PushUpTracker buildPushUpTracker(String comment, Integer pushUpCount, String username) {
        PushUpTracker pushUpTrackerToSave = PushUpTracker.builder()
                .username(username.toLowerCase())
                .pushUpCount(pushUpCount)
                .comment(comment)
                .timeStamp(LocalDateTime.now())
                .build();
        return pushUpTrackerToSave;
    }
}

