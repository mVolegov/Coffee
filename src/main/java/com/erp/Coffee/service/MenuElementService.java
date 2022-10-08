package com.erp.Coffee.service;

import com.erp.Coffee.controller.admin.exception.MenuElementContainedInOrderException;
import com.erp.Coffee.controller.admin.exception.MenuElementNotFoundException;
import com.erp.Coffee.model.MenuComposition;
import com.erp.Coffee.model.MenuElement;
import com.erp.Coffee.repository.MenuElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class MenuElementService {

    private final MenuElementRepository menuElementRepository;
    private final OrderCompositionService orderCompositionService;

    @Autowired
    public MenuElementService(MenuElementRepository menuElementRepository,
                              OrderCompositionService orderCompositionService) {
        this.menuElementRepository = menuElementRepository;
        this.orderCompositionService = orderCompositionService;
    }

    public List<MenuElement> findAll() {
        List<MenuElement> allMenuElements = menuElementRepository.findAll();

        BigDecimal costPrice = BigDecimal.ZERO;
        for (MenuElement eachMenuElement : allMenuElements) {
            Set<MenuComposition> menuItems = eachMenuElement.getMenuItem();

            for (MenuComposition eachMenuItem : menuItems) {
                 costPrice = costPrice.add(eachMenuItem.getWarehouseItem().getPricePerUnit()
                        .multiply(BigDecimal.valueOf(eachMenuItem.getRequiredAmount())));

//                 costPrice = costPrice.add(costPrice);
            }

            eachMenuElement.setCostPrice(costPrice);
        }

        return menuElementRepository.findAll();
    }

    public void saveMenuElement(MenuElement menuElement) {
        menuElementRepository.save(menuElement);
    }

    public void deleteMenuElementById(long id) {
        if(orderCompositionService.ifMenuElementContainedInAnyOrder(id)) {
            throw new MenuElementContainedInOrderException("Пункт меню содержится в заказе");
        } else {
            menuElementRepository.deleteById(id);
        }
    }

    public MenuElement findMenuElementById(long id) {
        return menuElementRepository.findById(id)
                .orElseThrow(() -> new MenuElementNotFoundException("Элемента с таким id нет"));
    }
}
