package com.example.products.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "list2product")
@Accessors(chain = true)
public class ListToProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "list_id")
    private long listId;

    @Column(name = "product_id")
    private long productId;
}
