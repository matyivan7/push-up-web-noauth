package com.project.push_up_web_noauth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "push_up_tracker")
public class PushUpTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Integer pushUpCount;

    private String comment;

    private LocalDateTime timeStamp;

    public PushUpTracker(String username, Integer pushUpCount, String comment) {
        this.username = username;
        this.pushUpCount = pushUpCount;
        this.comment = comment;
    }
}
