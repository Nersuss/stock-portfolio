package ru.nersus.stock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"owner", "symbol"})})
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    String symbol;
    @Column(nullable = false)
    int count;
    @ManyToOne
    User owner;
}
