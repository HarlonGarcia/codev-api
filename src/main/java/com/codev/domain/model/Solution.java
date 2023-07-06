package com.codev.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_solution")
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
