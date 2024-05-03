package com.codev.domain.model;

import com.codev.domain.dto.form.SolutionDTOForm;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_solution")
public class Solution extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "repository_url", length = 150, nullable = false)
    private String repositoryUrl;

    @Column(name = "deploy_url", length = 150)
    private String deployUrl;

    @ManyToMany
    @JoinTable(name = "tb_like",
            joinColumns = @JoinColumn(name = "solution_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<User> participants;

    public Solution(SolutionDTOForm solutionDTOForm) {
        this.repositoryUrl = solutionDTOForm.getRepositoryUrl();
        this.deployUrl = solutionDTOForm.getDeployUrl();
    }

    public Solution(){}

}
