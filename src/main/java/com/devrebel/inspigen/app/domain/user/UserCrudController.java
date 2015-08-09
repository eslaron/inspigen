package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.web.AbstractCrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserCrudController extends AbstractCrudController<User,Long,UserRepository> {

}