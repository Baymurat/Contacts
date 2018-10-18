package com.itechart.contacts.core.dao;

import com.itechart.contacts.core.utils.error.CustomException;

import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 12.09.2018
 */
public interface DAO<E, K> {
    E getEntityById(K id) throws CustomException;
    boolean update(E e) throws CustomException;
    boolean delete(K k) throws CustomException;
    int insert(E e) throws CustomException;
    Map<K, E> getRecords(int from, int range);
}
