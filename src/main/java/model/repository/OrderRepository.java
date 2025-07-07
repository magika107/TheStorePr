package model.repository;

import model.entity.Order;

import java.util.Collections;
import java.util.List;

public class OrderRepository implements Repository<Order>{
    @Override
    public void save(Order order) throws Exception {

    }

    @Override
    public void edit(Order order) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<Order> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Order findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
