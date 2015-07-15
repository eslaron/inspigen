package com.devrebel.inspigen.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<ID extends  Number, E extends Serializable> {

    JpaRepository<E,ID> repository;

    public void create(E entity) {
        repository.save(entity);
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public E findById(ID id) {
        return repository.findOne(id);
    }

    public void update(E entity) {
        repository.saveAndFlush(entity);
    }

    public void deleteById(ID id) {
        repository.delete(id);
    }
}
