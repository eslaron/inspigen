package com.devrebel.inspigen.app.domain.location;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devrebel.inspigen.core.web.AbstractCrudController;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationCrudController extends AbstractCrudController<Location,Long,LocationRepository> {

}