package com.project.push_up_web_noauth.service;

import com.project.push_up_web_noauth.entity.PushUpTracker;
import com.project.push_up_web_noauth.exception.UsernameNotFoundException;
import com.project.push_up_web_noauth.repository.PushUpTrackerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PushUpTrackerServiceTest {

    @Mock
    private PushUpTrackerRepository pushUpTrackerRepository;

    @InjectMocks
    private PushUpTrackerService pushUpTrackerService;

    private PushUpTracker pushUpTracker;

    private PushUpTracker pushUpTracker2;

    private PushUpTracker pushUpTracker3;

    private PushUpTracker pushUpTrackerToDelete;

    private String username = "jane_doe";


    @BeforeEach
    void setUp() {
        pushUpTracker = PushUpTracker.builder()
                .username("john_doe")
                .pushUpCount(100)
                .comment("Great effort!")
                .build();

        pushUpTracker2 = PushUpTracker.builder()
                .username(username)
                .pushUpCount(150)
                .comment("Keep going!")
                .build();

        pushUpTracker3 = PushUpTracker.builder()
                .username(username)
                .pushUpCount(80)
                .comment("Go get them!")
                .build();

        pushUpTrackerToDelete = PushUpTracker.builder()
                .id(1L)
                .username(username)
                .pushUpCount(40)
                .comment("You are the best!")
                .build();
    }

    @Test
    void testCreatePushUpTracker() {
        PushUpTracker pushUpTrackerToSave = PushUpTracker.builder()
                .username(pushUpTracker.getUsername())
                .pushUpCount(pushUpTracker.getPushUpCount())
                .comment(pushUpTracker.getComment())
                .build();

        when(pushUpTrackerRepository.save(argThat(p ->
                p.getUsername().equals(pushUpTracker.getUsername()) &&
                        Objects.equals(p.getPushUpCount(), pushUpTracker.getPushUpCount()) &&
                        p.getComment().equals(pushUpTracker.getComment()))))
                .thenReturn(pushUpTrackerToSave);

        PushUpTracker createdTracker = pushUpTrackerService.createPushUpTracker(pushUpTracker);

        assertNotNull(createdTracker);
        assertEquals("john_doe", createdTracker.getUsername());
        assertEquals(100, createdTracker.getPushUpCount());
        assertEquals("Great effort!", createdTracker.getComment());

        verify(pushUpTrackerRepository, times(1)).save(any(PushUpTracker.class));
    }

    @Test
    void testCreatePushUpTracker_nullPushUpTracker() {
        PushUpTracker nullPushUpTracker = null;

        assertThrows(NullPointerException.class, () -> {
            pushUpTrackerService.createPushUpTracker(nullPushUpTracker);
        });

        verify(pushUpTrackerRepository, times(0)).save(any(PushUpTracker.class));
    }

    @Test
    void testCreatePushUpTracker_nullFields() {
        PushUpTracker pushUpTrackerWithNullUsername = PushUpTracker.builder()
                .username(null)
                .pushUpCount(100)
                .comment("Missing username")
                .build();

        assertThrows(NullPointerException.class, () -> {
            pushUpTrackerService.createPushUpTracker(pushUpTrackerWithNullUsername);
        });

        verify(pushUpTrackerRepository, times(0)).save(any(PushUpTracker.class));
    }

    @Test
    void testGetAllPushUps() {
        List<PushUpTracker> pushUpTrackers = List.of(pushUpTracker, pushUpTracker2);
        when(pushUpTrackerRepository.findAll()).thenReturn(pushUpTrackers);

        List<PushUpTracker> result = pushUpTrackerService.getAllPushUps();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        assertEquals(100, result.get(0).getPushUpCount());
        assertEquals("Great effort!", result.get(0).getComment());

        assertEquals("jane_doe", result.get(1).getUsername());
        assertEquals(150, result.get(1).getPushUpCount());
        assertEquals("Keep going!", result.get(1).getComment());

        verify(pushUpTrackerRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPushUpsForUser_validUser() {
        List<PushUpTracker> pushUpTrackers = List.of(pushUpTracker2, pushUpTracker3);
        when(pushUpTrackerRepository.findByUsername(username)).thenReturn(pushUpTrackers);

        List<PushUpTracker> result = pushUpTrackerService.getAllPushUpsForUser(username);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(username, result.get(0).getUsername());
        assertEquals(username, result.get(1).getUsername());

        verify(pushUpTrackerRepository, times(2)).findByUsername(username);
    }

    @Test
    void testGetAllPushUpsForUser_userNotFound() {
        when(pushUpTrackerRepository.findByUsername(username)).thenReturn(List.of());

        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            pushUpTrackerService.getAllPushUpsForUser(username);
        });

        assertEquals("Invalid username: jane_doe", thrown.getMessage());

        verify(pushUpTrackerRepository, times(1)).findByUsername(username);
    }

    @Test
    void testDeletePushUpTracker() {
        pushUpTrackerService.deletePushUpTracker(pushUpTrackerToDelete.getId());

        verify(pushUpTrackerRepository, times(1)).deleteById(pushUpTrackerToDelete.getId());
    }

    @Test
    void testDeletePushUpTracker_notFound() {
        doNothing().when(pushUpTrackerRepository).deleteById(pushUpTrackerToDelete.getId());

        pushUpTrackerService.deletePushUpTracker(pushUpTrackerToDelete.getId());

        verify(pushUpTrackerRepository, times(1)).deleteById(pushUpTrackerToDelete.getId());
    }
}
