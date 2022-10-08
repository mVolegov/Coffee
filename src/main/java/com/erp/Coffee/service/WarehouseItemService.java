package com.erp.Coffee.service;

import com.erp.Coffee.controller.admin.exception.WarehouseItemIsUsedInMenuException;
import com.erp.Coffee.controller.admin.exception.WarehouseItemNotFoundException;
import com.erp.Coffee.model.WarehouseItem;
import com.erp.Coffee.repository.WarehouseItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class WarehouseItemService {

    private final WarehouseItemRepository warehouseItemRepository;
    private final MenuCompositionService menuCompositionService;

    @Autowired
    public WarehouseItemService(WarehouseItemRepository warehouseItemRepository,
                                MenuCompositionService menuCompositionService) {
        this.warehouseItemRepository = warehouseItemRepository;
        this.menuCompositionService = menuCompositionService;
    }

    public List<WarehouseItem> findAll() {
        return warehouseItemRepository.findAll();
    }

    public WarehouseItem findWarehouseItemById(long id) {
        return warehouseItemRepository.findById(id)
                .orElseThrow(() -> new WarehouseItemNotFoundException("Элемента на складе не существует"));
    }

    public void saveWarehouseItem(WarehouseItem warehouseItem) {
        int amount = warehouseItem.getAmount();
        BigDecimal pricePerPackage = warehouseItem.getPricePerPackage().setScale(2, RoundingMode.HALF_UP);
        BigDecimal pricePerUnit = pricePerPackage.divide(BigDecimal.valueOf(amount), RoundingMode.DOWN);

        warehouseItem.setPricePerUnit(pricePerUnit);

        warehouseItemRepository.save(warehouseItem);
    }

    public void deleteWarehouseItemById(long id) {
        if (menuCompositionService.ifWareHouseItemContainedInMenuElement(id)) {
            throw new WarehouseItemIsUsedInMenuException("Пункт используется в меню");
        } else {
            warehouseItemRepository.deleteById(id);
        }
    }
}
