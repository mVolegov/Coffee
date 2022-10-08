package com.erp.Coffee.repository;

import com.erp.Coffee.model.MenuComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCompositionRepository extends JpaRepository<MenuComposition, Long> {

    Optional<List<MenuComposition>> findAllByMenuElement_Id(long id);
    Optional<List<MenuComposition>> findAllByWarehouseItem_Id(long id);

    @Transactional
    @Modifying
    @Query("delete from MenuComposition mc where mc.menuElement.id=:menuId and mc.warehouseItem.id=:warehouseId")
    void deleteWarehouseItemInMenuComposition(@Param("menuId") long menuId, @Param("warehouseId") long warehouseId);

    @Transactional
    @Modifying
    @Query("delete from MenuComposition mc where mc.menuElement.id=:menuId")
    void deleteAllWarehouseItemsByMenuElementId(@Param("menuId") long menuId);
}
