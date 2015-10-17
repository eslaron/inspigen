package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.web.AbstractCrudController;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserCrudController extends AbstractCrudController<User,Long,UserRepository> {

    @Autowired
    UserService userService;

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody User entity) {
        userService.createUser(entity);
        message = " Created";
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);
        response = jsonResponse.toString();

        return new ResponseEntity<String>(response, HTTP_RESPONSE_STATUS_CREATED);
    }

}