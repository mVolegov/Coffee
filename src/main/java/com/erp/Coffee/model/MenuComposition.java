package com.erp.Coffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_composition")
public class MenuComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuelement_id")
    private MenuElement menuElement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseitem_id")
    private WarehouseItem warehouseItem;

    @Column(name = "required_amount")
    private double requiredAmount;

    @Override
    public String toString() {
        return "MenuComposition{" +
                "id=" + id +
                ", menuElement=" + menuElement.toString() +
                ", warehouseItem=" + warehouseItem.toString() +
                ", requiredAmount=" + requiredAmount +
                '}';
    }
}
