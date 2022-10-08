package com.erp.Coffee.repository;

import com.erp.Coffee.model.WarehouseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Long> {
}
