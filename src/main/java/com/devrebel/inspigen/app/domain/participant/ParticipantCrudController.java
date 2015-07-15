package com.devrebel.inspigen.app.domain.participant;

import com.devrebel.inspigen.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantCrudController extends AbstractCrudController<Participant,Long,ParticipantRepository> {

}