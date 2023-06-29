package com.candle.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Candle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Integer quantity;
    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileCover fileCover;
}
