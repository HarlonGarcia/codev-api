package com.codev.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_challenge_technology")
@Data
public class ChallengeTechnology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "technology_id")
    private Technology technology;

}
