package com.drgym.drgym.controller;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityService.findById(id);
        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Activity> getAllActivitiesById(@RequestParam List<Long> ids) {
        return activityService.findAllById(ids);
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}