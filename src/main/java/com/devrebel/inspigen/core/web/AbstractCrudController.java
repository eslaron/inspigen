package com.devrebel.inspigen.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractCrudController<T, ID extends Serializable> {

    @Autowired
    JpaRepository<T,ID> repository;

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody @Valid T entity) {
        repository.save(entity);
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
    public void update(@RequestBody @Valid T entity) {
        repository.saveAndFlush(entity);
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ID id) {
        repository.delete(id);
    }
}