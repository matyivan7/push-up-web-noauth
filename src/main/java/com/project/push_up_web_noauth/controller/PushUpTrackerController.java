package com.project.push_up_web_noauth.controller;

import com.project.push_up_web_noauth.entity.PushUpTracker;
import com.project.push_up_web_noauth.service.PushUpTrackerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tracker")
public class PushUpTrackerController {

    private final PushUpTrackerService pushUpTrackerService;

    @Autowired
    public PushUpTrackerController(PushUpTrackerService pushUpTrackerService) {
        this.pushUpTrackerService = pushUpTrackerService;
    }

    @PostMapping("/new-push-up")
    public ResponseEntity<PushUpTracker> createPushUpTracker(@RequestBody PushUpTracker pushUpTracker) {
        log.info("Create push up tracker endpoint reached");

        if (pushUpTracker == null) {
            throw new NullPointerException("pushUpTracker parameter is null");
        } else {
        PushUpTracker pushUpTrackerSaved = pushUpTrackerService.createPushUpTracker(pushUpTracker);
        return ResponseEntity.ok(pushUpTrackerSaved);
        }
    }

    @GetMapping
    public ResponseEntity<List<PushUpTracker>> getAllPushUps() {
        log.info("Get all push ups endpoint reached");

        List<PushUpTracker> pushUpTrackerList = pushUpTrackerService.getAllPushUps();

        return ResponseEntity.ok(pushUpTrackerList);
    }
}
