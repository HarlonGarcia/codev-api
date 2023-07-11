package com.codev.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_solution")
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Column(name = "repository_url")
    private String repositoryUrl;

    @Column(name = "deploy_url")
    private String deployUrl;

    public Solution(Long id, Challenge challenge, String repositoryUrl, String deployUrl) {
        this.id = id;
        this.challenge = challenge;
        this.repositoryUrl = repositoryUrl;
        this.deployUrl = deployUrl;
    }

    public Solution(){}

}
