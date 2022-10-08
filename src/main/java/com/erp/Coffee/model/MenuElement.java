package com.erp.Coffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class MenuElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menuElement", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<MenuComposition> menuItem;

    @Override
    public String toString() {
        return "MenuElement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", menuItem=" + menuItem +
                '}';
    }

    //    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "menueitem_warehouseelement_table",
//    joinColumns = {
//            @JoinColumn(name = "menuelement_id", referencedColumnName = "id")
//    },
//    inverseJoinColumns = {
//            @JoinColumn(name = "warehouseelement_id", referencedColumnName = "id")
//    })
//    private Set<WarehouseItem> warehouseElements;
}
