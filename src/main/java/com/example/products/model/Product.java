package com.example.products.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "products")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "kcal")
    private int kcal;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "list2product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "list_id"))
    private Collection<List> lists;
}