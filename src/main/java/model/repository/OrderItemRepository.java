package model.repository;

import model.entity.OrderItem;

import java.util.Collections;
import java.util.List;

public class OrderItemRepository  implements Repository<OrderItem>{
    @Override
    public void save(OrderItem orderItem) throws Exception {

    }

    @Override
    public void edit(OrderItem orderItem) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<OrderItem> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public OrderItem findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
