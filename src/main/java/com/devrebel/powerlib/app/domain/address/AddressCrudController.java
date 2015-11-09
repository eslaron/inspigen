package com.devrebel.powerlib.app.domain.address;

import com.devrebel.powerlib.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressCrudController extends AbstractCrudController<Address,Long> {

}