package com.devrebel.inspigen.core.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SimpleService<ID extends Number, E extends Serializable> extends AbstractService<ID,E>{

}
