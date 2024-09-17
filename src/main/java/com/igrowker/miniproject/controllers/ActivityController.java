package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.services.interfaces.ActivityService;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

}
