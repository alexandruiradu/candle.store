package com.candle.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ChosenCandle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer chosenQuantity;
    @ManyToOne
    @JoinColumn
    private Candle candle;
    @ManyToOne
    @JoinColumn
    private ShoppingCart shoppingCart;
    @ManyToOne
    @JoinColumn
    private CustomerOrder customerOrder;

}
