package com.drgym.drgym.repository;

import com.drgym.drgym.model.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ActivityRepositoryTests {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void testSaveAndFindActivity() {
        Activity activity = new Activity(2137L, 1L, 10L, 10L, new Timestamp(0));

        Activity savedActivity = activityRepository.save(activity);

        assertNotNull(savedActivity);
        assertEquals(2137L, savedActivity.getId());

        Activity foundActivity = activityRepository.findById(savedActivity.getId()).orElse(null);
        assertNotNull(foundActivity);
        assertEquals(2137L, foundActivity.getId());

        activityRepository.deleteById(savedActivity.getId());
    }

    @Test
    void testUpdateActivity() {
        Activity activity = new Activity(2137L, 1L, 10L, 10L, new Timestamp(0));

        Activity savedActivity = activityRepository.save(activity);

        assertNotNull(savedActivity);
        assertEquals(2137L, savedActivity.getId());

        Activity foundActivity = activityRepository.findById(savedActivity.getId()).orElse(null);
        assertNotNull(foundActivity);
        assertEquals(2137L, foundActivity.getId());

        foundActivity.setReps(20L);
        foundActivity.setWeight(20L);
        foundActivity.setDuration(new Timestamp(1));

        Activity updatedActivity = activityRepository.save(foundActivity);
        assertNotNull(updatedActivity);
        assertEquals(2137L, updatedActivity.getId());
        assertEquals(20L, updatedActivity.getReps());
        assertEquals(20L, updatedActivity.getWeight());
        assertEquals(new Timestamp(1), updatedActivity.getDuration());

        activityRepository.deleteById(savedActivity.getId());
    }

    @Test
    void testDeleteActivity() {
        Activity activity = new Activity(2137L, 1L, 10L, 10L, new Timestamp(0));

        activityRepository.save(activity);

        activityRepository.deleteById(2137L);

        assertFalse(activityRepository.findById(2137L).isPresent());
    }
}
