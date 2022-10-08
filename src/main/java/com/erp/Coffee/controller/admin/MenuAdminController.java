package com.erp.Coffee.controller.admin;

import com.erp.Coffee.controller.admin.exception.MenuElementContainedInOrderException;
import com.erp.Coffee.model.MenuComposition;
import com.erp.Coffee.model.MenuElement;
import com.erp.Coffee.service.MenuCompositionService;
import com.erp.Coffee.service.MenuElementService;
import com.erp.Coffee.service.WarehouseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/menu")
@PreAuthorize("hasAuthority('ADMIN')")
public class MenuAdminController {

    private final MenuElementService menuElementService;
    private final WarehouseItemService warehouseItemService;
    private final MenuCompositionService menuCompositionService;

    @Autowired
    public MenuAdminController(MenuElementService menuElementService, WarehouseItemService warehouseItemService, MenuCompositionService compositionService) {
        this.menuElementService = menuElementService;
        this.warehouseItemService = warehouseItemService;
        this.menuCompositionService = compositionService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("menu_elements", menuElementService.findAll());

        return "admin/menu/main_page";
    }

    @GetMapping("/add_menu_element")
    public String createMenuElementPage(MenuElement menuElement, Model model) {
        model.addAttribute("menu_element", menuElement);

        return "admin/menu/add_menu_element";
    }

    @PostMapping("/add_menu_element")
    public String createMenuElement(MenuElement menuElement) {
        menuElementService.saveMenuElement(menuElement);

        return "redirect:/admin/menu";
    }

    @DeleteMapping("/delete_menu_element/{id}")
    public String deleteMenuElement(@PathVariable("id") long id) {
        try {
            menuElementService.deleteMenuElementById(id);

            return "redirect:/admin/menu";
        } catch (MenuElementContainedInOrderException e) {
            return "admin/menu/menu_element_contained_in_order";
        }
    }

    @GetMapping("/update_menu_element/{id}")
    public String updateMenuElementPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("menuelement", menuElementService.findMenuElementById(id));

        return "admin/menu/update_menu_element";
    }

    @PatchMapping("/update_menu_element")
    public String updateMenuElement(MenuElement menuElement) {
        menuElementService.saveMenuElement(menuElement);

        return "redirect:/admin/menu";
    }

    /**
     * Страница состава конкретного пункта меню
     */
    @GetMapping("/add_items_to_menu_element/{id}")
    public String addItemToMenuElementPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("menu_element", menuElementService.findMenuElementById(id));
        model.addAttribute("warehouse_items", warehouseItemService.findAll());
        model.addAttribute("menu_element_composition", menuCompositionService.findAll(id));

        return "admin/menu/add_items_to_menu_element";
    }

    /**
     * Страница добавления предмета со склада в пункт меню
     */
    @GetMapping("/{menu_element_id}/add_item_to_menu_element")
    public String addItemToMenuElementPage(@PathVariable("menu_element_id") long menuElementId,
                                           MenuComposition menuComposition,
                                           Model model) {
        model.addAttribute("menu_element", menuElementService.findMenuElementById(menuElementId));
        model.addAttribute("menu_element_composition", menuComposition);
        model.addAttribute("warehouse_items", warehouseItemService.findAll());

        return "admin/menu/add_item_to_menu_element";
    }

    @PostMapping("/{menu_element_id}/add_item_to_menu_element")
    public String addItemToMenuElement(@PathVariable("menu_element_id") long menuElementId,
                                       MenuComposition menuComposition) {
        menuCompositionService.saveByMenuElementId(menuElementId, menuComposition);

        return "redirect:/admin/menu/add_items_to_menu_element/{menu_element_id}";
    }

    @DeleteMapping("/{id_menu}/delete_items_from_menu_element/{id_warehouse}")
    public String deleteItemFromMenuElement(@PathVariable("id_menu") long idMenu,
                                            @PathVariable("id_warehouse") long idWarehouse) {
        menuCompositionService.deleteCompositionItemByMenuIdAndWarehouseId(idMenu, idWarehouse);

        return "redirect:/admin/menu/add_items_to_menu_element/{id_menu}";
    }
}
