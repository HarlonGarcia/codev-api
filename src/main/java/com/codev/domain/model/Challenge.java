package com.codev.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_challenge")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

}
