package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.models.Activity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityService {
    List<Activity> findAllByDestination(Long destinationId, int page, int size);
}
