package com.devrebel.powerlib.app.domain.person;

import com.devrebel.powerlib.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonCrudController extends AbstractCrudController<PersonalData,Long> {

}