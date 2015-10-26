package com.devrebel.inspigen.app.domain.person;

import com.devrebel.inspigen.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonCrudController extends AbstractCrudController<PersonalData,Long> {

}