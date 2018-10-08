package com.itechart.contacts.core.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 12.09.2018
 */
public interface DAO<E, K> {
    E getEntityById(K id);
    boolean update(E e);
    boolean delete(K k);
    int insert(E e);
    Map<K, E> getRecords(int from, int range);
}
