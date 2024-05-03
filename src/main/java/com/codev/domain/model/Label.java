package com.codev.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "tb_label")
public class Label {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column
    private String description;

}
