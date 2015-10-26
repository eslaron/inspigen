package com.devrebel.inspigen.app.domain.user;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserCrudController  {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository repository;

    @Autowired
    private Mapper dtoMapper;

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody @Valid UserAddDto userAddDto) {
        User userEntity = dtoMapper.map(userAddDto, User.class);
        userService.createUser(userEntity);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> findAll(){
        List<User> userEntities = repository.findAll();
        List<UserDto> users = new ArrayList<>();
        userEntities.forEach(userEntity -> users.add(dtoMapper.map(userEntity, UserDto.class)));
        return users;
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public UserDto findOne(@PathVariable Long id){
        User userEntity = repository.findOne(id);
        return dtoMapper.map(userEntity,UserDto.class);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
    public void update(@RequestBody @Valid UserAddDto userAddDto) {
        User userEntity = dtoMapper.map(userAddDto, User.class);
        repository.saveAndFlush(userEntity);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        repository.delete(id);
    }
}