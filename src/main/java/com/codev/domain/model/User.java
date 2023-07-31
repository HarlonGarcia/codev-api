package com.codev.domain.model;

import com.codev.domain.dto.form.UserDTOForm;
import com.codev.utils.GlobalConstants;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "additional_url")
    private String additionalUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(UserDTOForm userDTOForm) {
        this.name = userDTOForm.getName();
        this.email = userDTOForm.getEmail();
        this.password = userDTOForm.getPassword();
        this.active = GlobalConstants.ACTIVE;
        this.githubUrl = userDTOForm.getGithubUrl();
        this.additionalUrl = userDTOForm.getAdditionalUrl();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(){}

}