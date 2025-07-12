package model.service;

import model.entity.OrderItem;
import model.entity.Order;
import model.entity.Payment;
import model.repository.OrderItemRepository;
import model.repository.OrderRepository;

import java.util.Collections;
import java.util.List;

public class OrderService implements Service<Order> {
    private OrderItemService orderItemService = new OrderItemService();
    private PaymentService paymentService = new PaymentService();

    @Override
    public void save(Order order) throws Exception {
        int total = order.getOrderTotal();

        try (
                OrderRepository orderRepository = new OrderRepository();
        ) {

            for (OrderItem orderItem : order.getOrderItemList()) {
                orderItem.setOrder(order);
                orderItemService.save(orderItem);
            }

            for (Payment payment : order.getPaymentList()) {
                payment.setOrder(order);
                paymentService.save(payment);
            }
            orderRepository.save(order);
        }
    }

        @Override
        public void edit (Order order) throws Exception {

        }

        @Override
        public void delete ( int id) throws Exception {

        }

        @Override
        public List<Order> findAll () throws Exception {
            return Collections.emptyList();
        }

        @Override
        public Order findById ( int id) throws Exception {
            return null;
        }

        @Override
        public void close () throws Exception {

        }
    }
