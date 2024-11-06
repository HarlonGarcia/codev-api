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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "tb_challenge")
public class Challenge extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(length = 1500, nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "tb_challenge_technology",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private Set<Technology> technologies;

    @ManyToMany
    @JoinTable(name = "tb_participant",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    @JsonBackReference
    private List<User> participants;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Challenge(ChallengeDTOForm challengeDTOForm) {
        this.title = challengeDTOForm.getTitle();
        this.description = challengeDTOForm.getDescription();
        this.image = new Image(challengeDTOForm.getImage());
        this.active = GlobalConstants.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.endDate = createdAt.plusMonths(1);
        this.status = challengeDTOForm.getStatus();
        this.technologies = challengeDTOForm.getTechnologies()
            .stream()
            .map(Technology::new)
            .collect(Collectors.toSet());
    }

    public Challenge(){}
}