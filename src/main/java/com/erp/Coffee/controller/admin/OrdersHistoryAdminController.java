package com.erp.Coffee.controller.admin;

import com.erp.Coffee.service.OrderCompositionService;
import com.erp.Coffee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders_history")
@PreAuthorize("hasAuthority('ADMIN')")
public class OrdersHistoryAdminController {

    private final OrderService orderService;

    @Autowired
    public OrdersHistoryAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("sum", orderService.countSumOfAllOrders());

        return "admin/orders/orders_history";
    }

    @GetMapping("/{order_id}")
    public String orderDetails(@PathVariable("order_id") long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderDetailsByOrderId(orderId));

        return "admin/orders/order_details";
    }
}
