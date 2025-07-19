package model.service;

import lombok.Getter;
import model.entity.OrderItem;
import model.entity.Order;
import model.entity.Payment;

import model.repository.OrderRepository;

import java.util.List;

public class OrderService implements Service<Order> {
    @Getter
    private static OrderService service = new OrderService();

    private OrderService() {

    }

    @Override
    public void save(Order order) throws Exception {
        try (OrderRepository orderRepository = new OrderRepository()) {

            orderRepository.save(order);

            for (OrderItem orderItem : order.getOrderItemList()) {
                orderItem.setOrder(order);
                orderItemService.save(orderItem);
            }

            for (Payment payment : order.getPaymentList()) {
                payment.setOrder(order);
                paymentService.save(payment);
            }
        }
    }

    @Override
    public void edit(Order order) throws Exception {
        try (OrderRepository orderRepository = new OrderRepository()) {
            if (orderRepository.findById(order.getId()) != null) {
                orderRepository.edit(order);
            } else {
                throw new Exception("Order not found");
            }
        }

    }

    @Override
    public void delete(int id) throws Exception {
        try (OrderRepository orderRepository = new OrderRepository()) {
            if (orderRepository.findById(id) != null) {
                orderRepository.delete(id);
            } else {
                throw new Exception("Order not found");
            }
        }

    }

    @Override
    public List<Order> findAll() throws Exception {
        try (OrderRepository orderRepository = new OrderRepository()) {
            return orderRepository.findAll();
        }
    }

    @Override
    public Order findById(int id) throws Exception {
        try (OrderRepository orderRepository = new OrderRepository()) {
            return orderRepository.findById(id);
        }
    }
}
