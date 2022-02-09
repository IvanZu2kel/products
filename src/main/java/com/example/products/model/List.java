package com.example.products.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "lists")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "list2product",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Collection<Product> products;
}
