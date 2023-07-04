package com.candle.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerOrderId;
    private String shippingAddress;
    @ManyToOne
    @JoinColumn
    private User user;
    @OneToMany(mappedBy = "customerOrder")
    private List<ChosenCandle> chosenCandles;
}
