package com.devrebel.inspigen.app.domain.address;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devrebel.inspigen.core.web.AbstractCrudController;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressCrudController extends AbstractCrudController<Address,AddressDto,Long> {

}