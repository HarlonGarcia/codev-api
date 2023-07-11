package com.codev.domain.model;

import com.codev.domain.dto.form.ChallengeDTOForm;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "tb_challenge")
public class Challenge extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(mappedBy = "challenge")
    private List<UserChallenge> userChallenges;

    public Challenge(ChallengeDTOForm challengeDTOForm) {
        this.title = challengeDTOForm.getTitle();
        this.description = challengeDTOForm.getDescription();
    }

    public Challenge(){}

}
