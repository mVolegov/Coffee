package com.erp.Coffee.controller;

import com.erp.Coffee.model.Order;
import com.erp.Coffee.model.OrderComposition;
import com.erp.Coffee.service.MenuElementService;
import com.erp.Coffee.service.OrderCompositionService;
import com.erp.Coffee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/barista")
@PreAuthorize("hasAuthority('BARISTA')")
public class BaristaController {

    private final OrderService orderService;
    private final MenuElementService menuElementService;
    private final OrderCompositionService orderCompositionService;

    @Autowired
    public BaristaController(OrderService orderService,
                             MenuElementService menuElementService,
                             OrderCompositionService orderCompositionService) {
        this.orderService = orderService;
        this.menuElementService = menuElementService;
        this.orderCompositionService = orderCompositionService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("order", new Order());

        return "barista/main_page";
    }

    @PostMapping("/new_order")
    public String newOrder(Order order) {
        orderService.saveEmptyOrder(order);

        return "redirect:/barista/new_order";
    }

    @GetMapping("/new_order")
    public String newOrderPage(Model model) {
        List<OrderComposition> menuElementsIncludedInOrder =
                orderCompositionService.findMenuElementsIncludedInOrder(orderService.findLatestOrder().getId());
        model.addAttribute("order_compositions", menuElementsIncludedInOrder);

        return "barista/new_order";
    }

    @GetMapping("/add_new_element_to_order")
    public String newMenuElementToOrderPage(Model model) {
        model.addAttribute("menu_elements", menuElementService.findAll());
        model.addAttribute("order_composition", new OrderComposition());

        return "barista/add_new_element_to_order";
    }

    @PostMapping("/add_new_element_to_order")
    public String newMenuElementToOrder(OrderComposition orderComposition) {
        Order latestOrder = orderService.findLatestOrder();
        orderComposition.setMadeOrder(latestOrder);
        orderCompositionService.save(orderComposition);

        return "redirect:/barista/new_order";
    }

    @DeleteMapping("/delete_menu_element_from_order/{order_composition_id}")
    public String deleteMenuElementFromOrder(@PathVariable("order_composition_id") long id) {
        orderCompositionService.deleteById(id);

        return "redirect:/barista/new_order";
    }

    @PostMapping("/confirm_new_order")
    public String confirmNewOrder() {
        Order latestOrder = orderService.findLatestOrder();
        latestOrder.setSum(orderCompositionService.countSumForOrder(latestOrder.getId()));
        orderService.save(latestOrder);

        return "redirect:/barista";
    }

    @DeleteMapping("/delete_new_order")
    public String deleteNewOrder() {
        long latestOrderId = orderService.findLatestOrder().getId();
        orderCompositionService.deleteByOrderId(latestOrderId);
        orderService.deleteByOrderId(latestOrderId);

        return "redirect:/barista";
    }
}
