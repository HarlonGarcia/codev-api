package com.codev.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_challenge_category")
@Data
public class ChallengeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
