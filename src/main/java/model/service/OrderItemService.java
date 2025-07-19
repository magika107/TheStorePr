package model.service;

import lombok.Getter;
import model.entity.OrderItem;
import model.repository.OrderItemRepository;

import java.util.Collections;
import java.util.List;

public class OrderItemService implements Service<OrderItem> {



    @Getter
    private static OrderItemService service = new OrderItemService();
    private OrderItemService() {

    }

    @Override
    public void save(OrderItem orderItem) throws Exception {
        try (OrderItemRepository orderItemRepository = new OrderItemRepository()) {
            orderItemRepository.save(orderItem);
        }
    }

    @Override
    public void edit(OrderItem orderItem) throws Exception {
        try (OrderItemRepository orderItemRepository = new OrderItemRepository()) {
            if (orderItemRepository.findById(orderItem.getId()) != null) {
                orderItemRepository.edit(orderItem);
            } else {
                throw new Exception("OrderItem not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (OrderItemRepository orderItemRepository = new OrderItemRepository()) {
            if (orderItemRepository.findById(id) != null) {
                orderItemRepository.delete(id);
            } else {
                throw new Exception("OrderItem not found");
            }
        }
    }

    @Override
    public List<OrderItem> findAll() throws Exception {
        try (OrderItemRepository orderItemRepository = new OrderItemRepository()) {
            return orderItemRepository.findAll();
        }
    }

    @Override
    public OrderItem findById(int id) throws Exception {
        try (OrderItemRepository orderItemRepository = new OrderItemRepository()) {
            return orderItemRepository.findById(id);
        }
    }

    public List<OrderItem> findByOrderId(int orderId) throws Exception {
        try (OrderItemRepository orderItemRepository = new OrderItemRepository()) {
            return orderItemRepository.findByOrderId(orderId);
        }
    }
}
