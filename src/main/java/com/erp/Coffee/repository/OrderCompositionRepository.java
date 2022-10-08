package com.erp.Coffee.repository;

import com.erp.Coffee.model.OrderComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderCompositionRepository extends JpaRepository<OrderComposition, Long> {

    @Transactional
    @Query(value = "SELECT * FROM order_contains_menu_element WHERE order_id = ?1", nativeQuery = true)
    Optional<List<OrderComposition>> findAllByOrderId(long orderId);

    @Transactional
    @Query(value = "SELECT * FROM order_contains_menu_element WHERE menuelement_id = ?1 AND order_id = ?2", nativeQuery = true)
    Optional<List<OrderComposition>> findAllByMenuElementIdAndOrderId(long menuElementId, long orderId);

    @Transactional
    @Query(value = "SELECT * FROM order_contains_menu_element WHERE menuelement_id = ?1", nativeQuery = true)
    Optional<List<OrderComposition>> findAllByMenuElementId(long menuElementId);
}
