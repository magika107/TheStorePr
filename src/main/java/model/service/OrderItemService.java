package model.service;

import model.entity.OrderItem;
import model.repository.OrderItemRepository;

import java.util.Collections;
import java.util.List;

public class OrderItemService implements Service<OrderItem>{
    @Override
    public void save(OrderItem orderItem) throws Exception {
        try(OrderItemRepository orderItemRepository = new OrderItemRepository()){
            orderItemRepository.save(orderItem);
        }
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
