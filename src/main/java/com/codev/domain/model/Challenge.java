package com.codev.domain.model;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.utils.GlobalConstants;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column
    private boolean status;

    @ManyToMany
    @JoinTable(name = "tb_challenge_category",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Challenge(ChallengeDTOForm challengeDTOForm) {
        this.title = challengeDTOForm.getTitle();
        this.description = challengeDTOForm.getDescription();
        this.status = GlobalConstants.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.endDate = createdAt.plusMonths(1);
    }

    public Challenge(){}

}