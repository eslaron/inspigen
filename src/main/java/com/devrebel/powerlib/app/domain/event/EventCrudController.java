package com.devrebel.powerlib.app.domain.event;

import com.devrebel.powerlib.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventCrudController extends AbstractCrudController<Event,Long> {

}