package com.codev.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "tb_solution")
public class Solution extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "repository_url")
    private String repositoryUrl;

    @Column(name = "deploy_url")
    private String deployUrl;

    @ManyToMany
    @JoinTable(name = "tb_like",
            joinColumns = @JoinColumn(name = "solution_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<User> authors;

    public Solution(Long id, Challenge challenge, String repositoryUrl, String deployUrl) {
        this.id = id;
        this.challenge = challenge;
        this.repositoryUrl = repositoryUrl;
        this.deployUrl = deployUrl;
    }

    public Solution(){}

}
