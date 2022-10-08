package com.erp.Coffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_contains_menu_element")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order madeOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuelement_id")
    private MenuElement menuElement;

    @Column(name = "quantity")
    private double quantity;
}
