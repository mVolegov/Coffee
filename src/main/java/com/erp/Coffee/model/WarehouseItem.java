package com.erp.Coffee.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse_item")
public class WarehouseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private int amount;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "units")
    private String units;

    @NotNull(message = "Поле не должно быть пустым")
    @Column(name = "price_per_package")
    private BigDecimal pricePerPackage;

    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouseItem", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<MenuComposition> menuAppearances;

    @Override
    public String toString() {
        return "WarehouseItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", units='" + units + '\'' +
                ", pricePerPackage=" + pricePerPackage +
                ", pricePerUnit=" + pricePerUnit +
                ", menuAppearances=" + menuAppearances +
                '}';
    }

    //    @ManyToMany(mappedBy = "warehouseElements", fetch = FetchType.LAZY)
//    private Set<MenuElement> menueElements;
}
