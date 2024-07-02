package com.spring.repository;

import java.util.List;

public interface Dao<T> {

    T save(T t);

    T update(T t);

    void delete(T t);

    T findById(Long id);

    List<T> findAll();
}
