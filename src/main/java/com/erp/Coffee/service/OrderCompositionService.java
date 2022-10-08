package com.erp.Coffee.service;

import com.erp.Coffee.model.Order;
import com.erp.Coffee.model.OrderComposition;
import com.erp.Coffee.repository.OrderCompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Service
public class OrderCompositionService {

    private final OrderCompositionRepository orderCompositionRepository;

    @Autowired
    public OrderCompositionService(OrderCompositionRepository orderCompositionRepository) {
        this.orderCompositionRepository = orderCompositionRepository;
    }

    public List<OrderComposition> findMenuElementsIncludedInOrder(long orderId) {
        return orderCompositionRepository.findAllByOrderId(orderId).orElseThrow(RuntimeException::new);
    }

    public boolean ifMenuElementContainedInAnyOrder(long menuElementId) {
        List<OrderComposition> orderCompositions =
                orderCompositionRepository
                        .findAllByMenuElementId(menuElementId)
                        .orElseThrow(RuntimeException::new);

        return !orderCompositions.isEmpty();
    }

    public void save(OrderComposition orderComposition) {
        List<OrderComposition> orderCompositions =
                orderCompositionRepository
                        .findAllByMenuElementIdAndOrderId(orderComposition.getMenuElement().getId(),
                                orderComposition.getMadeOrder().getId())
                        .orElseThrow(RuntimeException::new);

        if (!orderCompositions.isEmpty()) {
            OrderComposition orderCompositionOld = orderCompositions.get(0);
            orderCompositionOld.setQuantity(orderComposition.getQuantity() + orderCompositionOld.getQuantity());

            orderCompositionRepository.save(orderCompositionOld);
        } else {
            orderCompositionRepository.save(orderComposition);
        }
    }

    public BigDecimal countSumForOrder(long orderId) {
        List<OrderComposition> orderCompositions =
                orderCompositionRepository
                        .findAllByOrderId(orderId)
                        .orElseThrow(RuntimeException::new);

        BigDecimal sum = BigDecimal.ZERO;
        for (OrderComposition orderComposition : orderCompositions) {
            BigDecimal price = orderComposition.getMenuElement().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(orderComposition.getQuantity());

            sum = sum.add(price.multiply(quantity));
        }

        return sum;
    }

    public void deleteByOrderId(long id) {
        List<OrderComposition> orderCompositions = orderCompositionRepository.findAllByOrderId(id).orElseThrow(RuntimeException::new);

        orderCompositionRepository.deleteAll(orderCompositions);
    }

    public void deleteById(long id) {
        orderCompositionRepository.deleteById(id);
    }
}
