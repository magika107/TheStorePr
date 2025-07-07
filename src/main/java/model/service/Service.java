package model.service;

import java.util.List;

public interface Service<T> extends AutoCloseable{
    void save(T t) throws Exception;
    void edit(T t) throws Exception;
    void delete(int id) throws Exception;
    List<T> findAll() throws Exception;
    T findById(int id) throws Exception;
}
