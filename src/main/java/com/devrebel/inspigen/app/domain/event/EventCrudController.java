package com.devrebel.inspigen.app.domain.event;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devrebel.inspigen.core.web.AbstractCrudController;

@RestController
@RequestMapping("/api/v1/events")
public class EventCrudController extends AbstractCrudController<Event,Long,EventRepository> {

}