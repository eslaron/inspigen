package com.devrebel.powerlib.app.domain.location;

import com.devrebel.powerlib.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationCrudController extends AbstractCrudController<Location,Long> {

}