package com.example.demo.dao;

import java.util.List;

public interface IDao<T> {
    boolean add(T t);
    boolean update(T t);
    boolean delete(int id);
    T findById(int id);
    List<T> findAll();
}
