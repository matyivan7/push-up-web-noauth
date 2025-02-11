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
    public ResponseEntity<PushUpTracker> createPushUpTracker(@RequestBody String comment, @RequestBody Integer pushUpCount, @RequestBody String username) {
        log.info("Create push up tracker endpoint reached");

        if (username.isEmpty() || pushUpCount == null) {
            throw new NullPointerException("Username or push up count parameter is null");
        } else {
        PushUpTracker pushUpTrackerSaved = pushUpTrackerService.createPushUpTracker(comment, pushUpCount, username);
        return ResponseEntity.ok(pushUpTrackerSaved);
        }
    }

    @GetMapping
    public ResponseEntity<List<PushUpTracker>> getAllPushUps() {
        log.info("Get all push ups endpoint reached");

        List<PushUpTracker> pushUpTrackerList = pushUpTrackerService.getAllPushUps();

        return ResponseEntity.ok(pushUpTrackerList);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PushUpTracker>> getAllPushUpTrackerForUser(@PathVariable String username) {
        log.info("Get all push ups for user endpoint reached");

        List<PushUpTracker> userPushUpTrackers = pushUpTrackerService.getAllPushUpsForUser(username);
        return ResponseEntity.ok(userPushUpTrackers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePushUpTracker(@PathVariable Long id) {
        log.info("Delete push up tracker endpoint reached");

        pushUpTrackerService.deletePushUpTracker(id);
        return ResponseEntity.ok().build();
    }
}
