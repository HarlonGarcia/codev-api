package com.codev.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_category")
public class Category extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Category(String categoryName) {
        this.name = categoryName;
    }

    public Category() {}

}
