package com.erp.Coffee.repository;

import com.erp.Coffee.model.MenuElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuElementRepository extends JpaRepository<MenuElement, Long> {
}
