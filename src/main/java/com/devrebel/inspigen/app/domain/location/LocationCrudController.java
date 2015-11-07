package com.devrebel.inspigen.app.domain.location;

import com.devrebel.inspigen.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationCrudController extends AbstractCrudController<Location,Long> {

}