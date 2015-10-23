package com.devrebel.inspigen.core.web;

import com.google.gson.JsonObject;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractCrudController<T, DTO, ID extends Serializable> {

    public static final HttpStatus HTTP_RESPONSE_STATUS_CREATED = HttpStatus.CREATED;
    public static final HttpStatus HTTP_RESPONSE_STATUS_OK = HttpStatus.OK;

    @Autowired
    JpaRepository<T,ID> repository;

    @Autowired
    private Mapper dtoMapper;

    private Class<T> entityClass;
    public String message = "";
    public String response = "";

    public AbstractCrudController()  {
        this.entityClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody DTO entityDto) {
        T objectEntity = dtoMapper.map(entityDto, entityClass);
        repository.save(objectEntity);

        message = "Created";
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);
        response = jsonResponse.toString();

        return new ResponseEntity<>(response, HTTP_RESPONSE_STATUS_CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<T> findAll(){
        return repository.findAll();
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public T findOne(@PathVariable ID id){
        return repository.findOne(id);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@RequestBody T data) {
        repository.saveAndFlush(data);
        message = "Updated";
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);
        response = jsonResponse.toString();

        return new ResponseEntity<>(response, HTTP_RESPONSE_STATUS_OK);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable ID id) {
        repository.delete(id);
        message = "Deleted";
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);
        response = jsonResponse.toString();

        return new ResponseEntity<>(response, HTTP_RESPONSE_STATUS_OK);
    }
}