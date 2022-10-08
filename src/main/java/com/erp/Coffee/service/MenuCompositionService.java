package com.erp.Coffee.service;

import com.erp.Coffee.model.MenuComposition;
import com.erp.Coffee.model.MenuElement;
import com.erp.Coffee.repository.MenuCompositionRepository;
import com.erp.Coffee.repository.MenuElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuCompositionService {

    private final MenuCompositionRepository menuCompositionRepository;
    private final MenuElementRepository menuElementRepository;

    @Autowired
    public MenuCompositionService(MenuCompositionRepository menuCompositionRepository, MenuElementRepository menuElementRepository) {
        this.menuCompositionRepository = menuCompositionRepository;
        this.menuElementRepository = menuElementRepository;
    }

    public List<MenuComposition> findAll(long id) {
        return menuCompositionRepository.findAllByMenuElement_Id(id).orElseThrow(RuntimeException::new);
    }

    public void saveByMenuElementId(long menuElementId, MenuComposition menuComposition) {
        MenuElement menuElement = menuElementRepository.findById(menuElementId).orElseThrow(RuntimeException::new);
        menuComposition.setMenuElement(menuElement);
        menuCompositionRepository.save(menuComposition);
    }

    public boolean ifWareHouseItemContainedInMenuElement(long warehouseItemId) {
        return !menuCompositionRepository
                        .findAllByWarehouseItem_Id(warehouseItemId)
                        .orElseThrow(RuntimeException::new)
                .isEmpty();
    }

    public void deleteCompositionItemByMenuIdAndWarehouseId(long idMenu, long idWarehouse) {
        menuCompositionRepository.deleteWarehouseItemInMenuComposition(idMenu, idWarehouse);
    }
}
