package com.devrebel.inspigen.app.domain.user;

import com.devrebel.inspigen.core.web.exception.message.MessageDTO;
import com.devrebel.inspigen.core.web.exception.message.MessageType;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    UserValidator userValidator;

    @Autowired
    private Mapper dtoMapper;

    @RequestMapping(method = RequestMethod.POST)
    public List<MessageDTO> create(@RequestBody @Valid UserAddDto userAddDto, BindingResult result, HttpServletResponse response) {
        User userEntity = dtoMapper.map(userAddDto, User.class);
        userValidator.validate(userEntity, result);
        List<MessageDTO> messages = new ArrayList<>();
        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                MessageDTO errorMessage = new MessageDTO(MessageType.ERROR, error.getCode());
                messages.add(errorMessage);
            });
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            MessageDTO successMessage = new MessageDTO(MessageType.SUCCESS,"User successfully added");
            messages.add(successMessage);
            userService.createUser(userEntity);
        }

        return messages;
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<UserDto> findAll(){
        List<User> userEntities = repository.findAll();
        List<UserDto> users = new ArrayList<>();
        userEntities.forEach(userEntity -> users.add(dtoMapper.map(userEntity, UserDto.class)));
        return users;
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    private UserDto findOne(@PathVariable Long id){
        User userEntity = repository.findOne(id);
        return dtoMapper.map(userEntity,UserDto.class);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
    private void update(@RequestBody @Valid UserAddDto userAddDto) {
        User userEntity = dtoMapper.map(userAddDto, User.class);
        repository.saveAndFlush(userEntity);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    private void delete(@PathVariable Long id) {
        repository.delete(id);
    }
}