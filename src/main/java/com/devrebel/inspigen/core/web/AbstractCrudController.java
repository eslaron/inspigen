package com.devrebel.inspigen.core.web;

import java.io.Serializable;
import java.util.List;

import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class AbstractCrudController<T, ID extends Serializable, R extends JpaRepository<T,ID>> {

    public static final HttpStatus HTTP_RESPONSE_STATUS_CREATED = HttpStatus.CREATED;
    public static final HttpStatus HTTP_RESPONSE_STATUS_OK = HttpStatus.OK;

    @Autowired
    R repository;

    Class<T> entityClass;
    String message = "";
    String response = "";

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody T entity) {
        repository.save(entity);
        message = entityClass.getName() + " Created";
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);
        response = jsonResponse.toString();

        return new ResponseEntity<String>(response, HTTP_RESPONSE_STATUS_CREATED);
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
        message = entityClass.getName() + " Updated";
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);
        response = jsonResponse.toString();

        return new ResponseEntity<String>(response, HTTP_RESPONSE_STATUS_OK);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable ID id) {
        repository.delete(id);
        message = entityClass.getName() + " Deleted";
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("message", message);
        response = jsonResponse.toString();

        return new ResponseEntity<String>(response, HTTP_RESPONSE_STATUS_OK);
    }
}