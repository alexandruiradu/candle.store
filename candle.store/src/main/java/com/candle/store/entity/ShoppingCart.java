package com.candle.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shoppingCartId;
    @OneToOne
    @JoinColumn
    private User user;
    @OneToMany(mappedBy = "shoppingCart")
    private List<ChosenCandle> chosenCandles;
}
