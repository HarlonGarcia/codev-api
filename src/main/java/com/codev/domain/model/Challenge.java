package com.codev.domain.model;

import com.codev.domain.dto.form.ChallengeDTOForm;
import com.codev.domain.enums.ChallengeStatus;
import com.codev.utils.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_challenge")
public class Challenge extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(length = 1500)
    private String description;


    @Column
    private boolean active;

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Column
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "tb_challenge_technology",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private List<Technology> technologies;

    @ManyToMany
    @JoinTable(name = "tb_participant",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    @JsonBackReference
    private List<User> participants;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Challenge(ChallengeDTOForm challengeDTOForm) {
        this.title = challengeDTOForm.getTitle();
        this.description = challengeDTOForm.getDescription();
        this.imageURL = challengeDTOForm.getImageURL();
        this.active = GlobalConstants.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.endDate = createdAt.plusMonths(1);
        this.status = challengeDTOForm.getStatus();
    }

    public Challenge(){}

}