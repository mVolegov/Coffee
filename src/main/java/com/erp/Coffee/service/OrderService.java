package com.erp.Coffee.service;

import com.erp.Coffee.model.Order;
import com.erp.Coffee.model.OrderComposition;
import com.erp.Coffee.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCompositionService orderCompositionService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderCompositionService orderCompositionService) {
        this.orderRepository = orderRepository;
        this.orderCompositionService = orderCompositionService;
    }

    public List<Order> findAll() {
        List<Order> allOrders = orderRepository.findAll();

        Collections.reverse(allOrders);

        return allOrders;
    }

    public void saveEmptyOrder(Order order) {
        order.setSum(BigDecimal.ZERO);
        order.setDateTime(new Date());
        orderRepository.save(order);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public Order findLatestOrder() {
        List<Order> allOrders = orderRepository.findAll();
        return allOrders.get(allOrders.size() - 1);
    }

    public void deleteByOrderId(long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderComposition> getOrderDetailsByOrderId(long orderId) {
        return orderCompositionService.findMenuElementsIncludedInOrder(orderId);
    }

    public BigDecimal countSumOfAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(Order::getSum)
                .reduce(BigDecimal::add)
                .orElseThrow(RuntimeException::new);
    }
}
