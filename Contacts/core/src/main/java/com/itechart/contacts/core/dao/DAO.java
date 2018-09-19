package com.itechart.contacts.core.dao;

import java.util.List;

/**
 * Created by Admin on 12.09.2018
 */
public interface DAO<E, K> {
    E getEntityById(K id);
    E update(E e);
    boolean delete(K id);
    boolean insert(E e);
    List<E> getAll();
}
