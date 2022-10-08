package com.erp.Coffee.controller.admin;

import com.erp.Coffee.controller.admin.exception.WarehouseItemIsUsedInMenuException;
import com.erp.Coffee.model.WarehouseItem;
import com.erp.Coffee.service.WarehouseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/wrh")
@PreAuthorize("hasAuthority('ADMIN')")
public class WarehouseAdminController {

    private final WarehouseItemService warehouseItemService;

    @Autowired
    public WarehouseAdminController(WarehouseItemService warehouseItemService) {
        this.warehouseItemService = warehouseItemService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("items", warehouseItemService.findAll());

        return "admin/warehouse/main_page";
    }

    @GetMapping("/add_wrh_item")
    public String createWarehouseItemPage(@ModelAttribute("warehouseitem") WarehouseItem warehouseItem) {
        return "admin/warehouse/add_warehouse_item";
    }

    @PostMapping("/add_wrh_item")
    public String createWarehouseItem(@ModelAttribute("warehouseitem") @Valid WarehouseItem warehouseItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/warehouse/add_warehouse_item";
        }

        System.out.println("Добавление:");
        warehouseItemService.saveWarehouseItem(warehouseItem);

        return "redirect:/admin/wrh";
    }

    @DeleteMapping("/delete_item/{id}")
    public String deleteWarehouseItem(@PathVariable("id") long id) {
        try {
            warehouseItemService.deleteWarehouseItemById(id);

            return "redirect:/admin/wrh";
        } catch (WarehouseItemIsUsedInMenuException e) {
            return "admin/warehouse/item_is_used_in_menu";
        }
    }

    @GetMapping("/update_item/{id}")
    public String updateWarehouseItemPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("item", warehouseItemService.findWarehouseItemById(id));

        return "admin/warehouse/update_warehouse_item";
    }

    @PatchMapping("/update_item")
    public String updateWarehouseItem(@ModelAttribute("item") @Valid WarehouseItem warehouseItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/warehouse/update_warehouse_item";
        }

        System.out.println("Обновление:");
        warehouseItemService.saveWarehouseItem(warehouseItem);

        return "redirect:/admin/wrh";
    }
}
